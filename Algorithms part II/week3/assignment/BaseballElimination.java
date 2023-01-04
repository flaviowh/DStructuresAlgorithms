package week3.assignment;

import java.util.Arrays;

import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

public class BaseballElimination {
    private int N;
    private String[] teams;
    private int[] wins;
    private int[] losses;
    private int[] remaining;
    private int[][] leftAgainst;
    private IllegalArgumentException teamNameException = new IllegalArgumentException("check the team name.");
    private double infiniteCap = Double.POSITIVE_INFINITY;
    private int sink;

    // create a baseball division from given filename in format specified below
    public BaseballElimination(String filename) {

        In input = new In(filename);
        N = input.readInt();
        teams = new String[N];
        wins = new int[N];
        remaining = new int[N];
        losses = new int[N];
        leftAgainst = new int[N][N];
        sink = N + 1;

        String[] lines = input.readAllLines();
        for (int i = 1; i < lines.length; i++) { // skip the first line
            String[] fields = lines[i].replaceAll("\\s+", " ").split(" ");
            teams[i - 1] = fields[0];
            wins[i - 1] = Integer.parseInt(fields[1]);
            losses[i - 1] = Integer.parseInt(fields[2]);
            remaining[i - 1] = Integer.parseInt(fields[3]);
            for (int x = 1; x < N + 1; x++) {
                int matches = Integer.parseInt(fields[3 + x]);
                leftAgainst[i - 1][x - 1] = matches;
            }
        }
    }

    private int teamID(String name) {
        if (name == null)
            throw teamNameException;

        for (int i = 0; i < teams.length; i++) {
            if (name.equals(teams[i])) {
                return i;
            }
        }
        throw teamNameException;
    }

    private boolean easilyEliminated(int t) {
        int possibleWins = wins[t] + remaining[t];
        for (int i = 0; i < N; i++) {
            if (wins[i] > possibleWins) {
                return true;
            }
        }
        return false;
    }

    private boolean networkEliminated(int teamId) {
        FlowNetwork network = matchesNetwork(teamId);
        FordFulkerson FF = new FordFulkerson(network, 0, sink);
        for (FlowEdge e : network.adj(0)) {
            if (e.capacity() - e.flow() > 0) {
                return true;
            }
        }
        return false;
    }

    // make the graph edges
    private FlowNetwork matchesNetwork(int teamId) {
        Queue<FlowEdge> edgesQueue = new Queue<>();
        // how many points the focus team can make
        int focusTeamMaxWins = wins[teamId] + remaining[teamId];

        int[][] matchesMatrix = leftAgainst.clone();

        int X = sink + 1; // matches vertexes reference

        for (int i = 0; i < matchesMatrix.length; i++) {
            int[] matches = matchesMatrix[i];

            for (int j = 0; j < matches.length; j++) {
                int gamesleft = matches[j];
                if (gamesleft > 0) {
                    FlowEdge sourceToMatchEdge = new FlowEdge(0, X, gamesleft);
                    FlowEdge matchToTeam1Edge = new FlowEdge(X, i + 1, infiniteCap);
                    FlowEdge matchToTeam2Edge = new FlowEdge(X, j + 1, infiniteCap);
                    edgesQueue.enqueue(sourceToMatchEdge);
                    edgesQueue.enqueue(matchToTeam1Edge);
                    edgesQueue.enqueue(matchToTeam2Edge);

                    // avoid duplicate edge creation
                    matchesMatrix[j][i] = 0;

                    // update matches reference
                    X++;
                }
            }

            int allowedPoints = focusTeamMaxWins - wins[i];
            FlowEdge teamToSinkEdge = new FlowEdge(i + 1, sink, allowedPoints);
            edgesQueue.enqueue(teamToSinkEdge);
        }
        // create and fill the network
        FlowNetwork network = new FlowNetwork(edgesQueue.size());
        while (!edgesQueue.isEmpty()) {
            network.addEdge(edgesQueue.dequeue());
        }

        return network;
    }

    // number of teams
    public int numberOfTeams() {
        return N;
    }

    // all teams
    public Iterable<String> teams() {
        Queue<String> TQ = new Queue<>();
        for (String team : teams) {
            TQ.enqueue(team);
        }
        return TQ;
    }

    // number of wins for given team
    public int wins(String team) {
        int id = teamID(team);
        return wins[id];
    }

    // number of losses for given team
    public int losses(String team) {
        int id = teamID(team);
        return losses[id];
    }

    // number of remaining games for given team
    public int remaining(String team) {
        int id = teamID(team);
        return remaining[id];
    }

    // number of remaining games between team1 and team2
    public int against(String team1, String team2) {
        int id = teamID(team1);
        int id2 = teamID(team2);
        return leftAgainst[id][id2];
    }

    // is given team eliminated?
    public boolean isEliminated(String team) {
        int id = teamID(team);

        if (easilyEliminated(id)) {
            return true;
        }

        return networkEliminated(id);
    }

    public Iterable<String> certificateOfElimination(String team) {
        return new Queue<String>();
    }

    private void readTest() {
        StdOut.println(Arrays.toString(teams));
        StdOut.println(Arrays.toString(wins));
        StdOut.println(Arrays.toString(losses));
        StdOut.println(Arrays.toString(remaining));
        StdOut.println("matches: ");
        for (int i = 0; i < N; i++) {
            StdOut.println(Arrays.toString(leftAgainst[i]));
        }
    }

    public static void main(String[] args) {
        String path = ".\\Algorithms part II\\week3\\assignment\\teams2.txt";
        BaseballElimination be = new BaseballElimination(path);
        //be.readTest();

        for (String team : be.teams()) {
            StdOut.println(be.isEliminated(team) ? team + " is eliminated" : team + " is not eliminated.");
            // StdOut.println(be.wins(team));
        }
    }

}