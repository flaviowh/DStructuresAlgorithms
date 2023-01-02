from typing import List
from collections import deque
from heapq import heappop, heappush


class DirectedEdge:
    def __init__(self, v: int, w: int, weight: float):
        self.v = v
        self.w = w
        self.weight = weight

    def from_(self) -> int:
        return self.v

    def to(self):
        return self.w

    def weight_(self) -> float:
        return self.weight

    def __lt__(self, other): # less than
        return self.weight < other.weight

    def __gt__(self, other): # greater then
        return self.weight > other.weight

    def __eq__(self, other):  # equal to
        return self.weight == other.weight

    def __repr__(self):
        return f"{self.v} - {self.w}  {self.weight}"


class EdgeWeightedDigraph:
    def __init__(self, v):
        self._V = v
        self._adj = [[] for _ in range(v)]

    def addEdge(self, e: DirectedEdge):
        self._adj[e.from_()].append(e)

    def adj(self, v) -> List[List[DirectedEdge]]:
        return self._adj[v]

    def V(self) -> int:
        return self._V

    def edges(self):
        unique_edges = set()
        for b in self._adj:
            for e in b:
                unique_edges.append(e)
        return unique_edges


class DijkstraSP:
    def __init__(self, G: EdgeWeightedDigraph, s: int):
        self.edgeTo = [None] * G.V()
        self.distTo = [float('inf')] * G.V()
        self.pq = []

        self.distTo[s] = 0.0
        heappush(self.pq, (0.0, s))

        while self.pq:
            _, v = heappop(self.pq)
            for e in G.adj(v):
                self.relax(e)

    def distTo(self, v: int) -> float:
        return self.distTo[v]

    def relax(self, e: DirectedEdge):
        v = e.from_()
        w = e.to()
        weight = e.weight_()
        if self.distTo[w] > self.distTo[v] + weight:
            self.distTo[w] = self.distTo[v] + weight
            self.edgeTo[w] = e
            heappush(self.pq, (self.distTo[w], w))

    def pathTo(self, v: int) -> List[List[DirectedEdge]]:
        path = deque()
        e = self.edgeTo[v]
        while e is not None:
            path.appendleft(e)
            e = self.edgeTo[e.from_()]
        return path


def main():
    G = EdgeWeightedDigraph(8)
    G.addEdge(DirectedEdge(0, 1, 5))
    G.addEdge(DirectedEdge(0, 4, 9))
    G.addEdge(DirectedEdge(0, 7, 8))
    G.addEdge(DirectedEdge(1, 2, 12))
    G.addEdge(DirectedEdge(1, 3, 15))
    G.addEdge(DirectedEdge(1, 7, 4))
    G.addEdge(DirectedEdge(2, 3, 3))
    G.addEdge(DirectedEdge(2, 6, 11))
    G.addEdge(DirectedEdge(3, 6, 9))
    G.addEdge(DirectedEdge(4, 5, 4))
    G.addEdge(DirectedEdge(4, 6, 20))
    G.addEdge(DirectedEdge(4, 7, 5))
    G.addEdge(DirectedEdge(5, 2, 1))
    G.addEdge(DirectedEdge(5, 6, 13))
    G.addEdge(DirectedEdge(7, 5, 6))
    G.addEdge(DirectedEdge(7, 2, 7))

    dk = DijkstraSP(G, 0)
    target = 6
    for e in dk.pathTo(target):
        print(e)


main()
