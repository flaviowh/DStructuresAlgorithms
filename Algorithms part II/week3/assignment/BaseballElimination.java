package week3.assignment;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class BaseballElimination {
    private final int numTeams;
    private final Map<String, Integer> teams = new HashMap<String, Integer>();
    private final int[] wins;
    private final int[] losses;
    private final int[] remaining;
    private final int[][] vsMatrix;
    private int[] eliminated;
    private Map<Integer, Set<String>> eliminatedBy = new HashMap<Integer, Set<String>>();
    private final int sink;

    // create a baseball division from given filename in format specified
    // divisionlow
    public BaseballElimination(String filename) {

        In input = new In(filename);
        numTeams = input.readInt();
        wins = new int[numTeams];
        remaining = new int[numTeams];
        losses = new int[numTeams];
        vsMatrix = new int[numTeams][numTeams];
        eliminated = new int[numTeams];
        sink = numTeams + 1;

        boolean hasGamesLeft = false;
        int leadScore = 0;

        String[] lines = input.readAllLines();
        for (int i = 1; i < lines.length; i++) { // skip the first line
            String[] fields = lines[i].trim().split("\\s+");
            teams.put(fields[0], i - 1);
            wins[i - 1] = Integer.parseInt(fields[1]);
            losses[i - 1] = Integer.parseInt(fields[2]);
            remaining[i - 1] = Integer.parseInt(fields[3]);
            for (int x = 1; x < numTeams + 1; x++) {
                int matches = Integer.parseInt(fields[3 + x]);
                if (!hasGamesLeft && matches > 0)
                    hasGamesLeft = true;
                vsMatrix[i - 1][x - 1] = matches;
            }

            if (Integer.parseInt(fields[1]) > leadScore) {
                leadScore = Integer.parseInt(fields[1]);
            }
        }

        calculateEliminations(leadScore, hasGamesLeft);
    }

    private void calculateEliminations(int leadScore, boolean hasGamesLeft) {

        if (numTeams == 1) {
            eliminated[0] = wins[0] < losses[0] ? 0 : 1;
            return;
        }

        if (!hasGamesLeft) {
            finishedSeasonSummary(leadScore);
            return;
        }

        for (String team : teams.keySet()) {
            int id = teams.get(team);
            eliminatedBy.put(id, new HashSet<String>()); // empty set

            if (easilyEliminated(id) || networkEliminated(id)) {
                eliminated[id] = 1;
            }

        }
    }

    private void finishedSeasonSummary(int leadScore) {
        Set<String> winners = new HashSet<String>();
        for (String team : teams.keySet()) {
            if (wins[teams.get(team)] == leadScore) {
                eliminated[teams.get(team)] = 0;
                winners.add(team);
            } else {
                eliminated[teams.get(team)] = 1;
                eliminatedBy.put(teams.get(team), winners);
            }
        }
        return;
    }

    public Iterable<String> certificateOfElimination(String team) {
        if (!teams.containsKey(team))
            throw new IllegalArgumentException("Invalid team name.");

        int id = teams.get(team);

        if (numTeams <= 1 || eliminated[id] == 0) {
            return null;
        }

        return eliminatedBy.get(id);
    }

    private boolean easilyEliminated(int id) {
        int possibleWins = wins[id] + remaining[id];
        boolean isOut = false;

        for (String team : teams.keySet()) {
            if (wins[teams.get(team)] > possibleWins) {
                isOut = true;

                eliminatedBy.get(id).add(team);

            }
        }

        return isOut;

    }

    private boolean networkEliminated(int teamId) {
        FlowNetwork network = matchesNetwork(teamId);
        FordFulkerson FF = new FordFulkerson(network, 0, sink);
        boolean isOut = false;

        for (FlowEdge e : network.adj(0)) {
            if (e.capacity() - e.flow() > 0) {
                isOut = true;
            }
        }

        if (isOut) {
            for (int i = 1; i < numTeams + 1; i++) {
                if (FF.inCut(i)) {
                    for (Map.Entry<String, Integer> entry : teams.entrySet()) {
                        if (entry.getValue().equals(i - 1)) {
                            String name = entry.getKey();
                            eliminatedBy.get(teamId).add(name);
                            break;
                        }

                    }
                }
            }
        }
        return isOut;
    }

    // make the graph edges
    private FlowNetwork matchesNetwork(int teamId) {
        Set<FlowEdge> edgesSet = new HashSet<FlowEdge>();
        Set<String> matchSet = new HashSet<>();
        // how many points the focus team can make
        int focusTeamMaxWins = wins[teamId] + remaining[teamId];

        int m = sink + 1; // matches vertexes reference

        for (int i = 0; i < vsMatrix.length; i++) {
            int[] matches = vsMatrix[i];

            for (int j = 0; j < matches.length; j++) {
                int gamesleft = matches[j];
                if (gamesleft > 0) {
                    if (!matchSet.contains(i + "-" + j)) { // avoid match edge duplication
                        FlowEdge sourceToMatchEdge = new FlowEdge(0, m, gamesleft);
                        FlowEdge matchToTeam1Edge = new FlowEdge(m, i + 1, Double.POSITIVE_INFINITY);
                        FlowEdge matchToTeam2Edge = new FlowEdge(m, j + 1, Double.POSITIVE_INFINITY);
                        edgesSet.add(sourceToMatchEdge);
                        edgesSet.add(matchToTeam1Edge);
                        edgesSet.add(matchToTeam2Edge);

                        // avoid match edge duplication
                        matchSet.add(j + "-" + i);

                        // update matches reference
                        m++;
                    }
                }
            }

            int allowedPoints = focusTeamMaxWins - wins[i];
            FlowEdge teamToSinkEdge = new FlowEdge(i + 1, sink, allowedPoints > 0 ? allowedPoints : 0);
            edgesSet.add(teamToSinkEdge);
        }
        // create and fill the network
        FlowNetwork network = new FlowNetwork(edgesSet.size());
        for (FlowEdge e : edgesSet) {
            network.addEdge(e);
        }

        return network;
    }

    // number of teams
    public int numberOfTeams() {
        return numTeams;
    }

    // all teams
    public Iterable<String> teams() {
        return teams.keySet();
    }

    // number of wins for given team
    public int wins(String team) {
        if (!teams.containsKey(team))
            throw new IllegalArgumentException("Invalid team name.");

        return wins[teams.get(team)];
    }

    // number of losses for given team
    public int losses(String team) {

        if (!teams.containsKey(team))
            throw new IllegalArgumentException("Invalid team name.");

        return losses[teams.get(team)];
    }

    // number of remaining games for given team
    public int remaining(String team) {
        if (!teams.containsKey(team))
            throw new IllegalArgumentException("Invalid team name.");

        return remaining[teams.get(team)];
    }

    // number of remaining games divisiontween team1 and team2
    public int against(String team1, String team2) {
        if (!teams.containsKey(team1) || !teams.containsKey(team2))
            throw new IllegalArgumentException("Invalid team name.");

        return vsMatrix[teams.get(team1)][teams.get(team2)];
    }

    // is given team eliminated?
    public boolean isEliminated(String team) {
        if (!teams.containsKey(team))
            throw new IllegalArgumentException("Invalid team name.");

        return eliminated[teams.get(team)] == 1;
    }

    public static void main(String[] args) {
        String path = "Algorithms part II\\week3\\assignment\\teams5.txt";
        BaseballElimination division = new BaseballElimination(path);
        // division.readTest();

        // for (String team : division.teams()) {
        // StdOut.println(division.isEliminated(team) ? team + " is eliminated." : team
        // + " is not eliminated.");

        // }

        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + " ");
                }
                StdOut.println("}");
            }
        }

    }
}
