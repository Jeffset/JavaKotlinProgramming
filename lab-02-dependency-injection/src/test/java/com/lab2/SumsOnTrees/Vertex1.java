package com.lab2.SumsOnTrees;

import javax.inject.Inject;

public class Vertex1 {

    private int subtree_sum;
    private int vertex_number;

    @Inject
    public Vertex1(Vertex2 first_vertex, Vertex3 second_vertex) {
        vertex_number = 5;
        subtree_sum = vertex_number + first_vertex.GetSubtreeSum() +
                second_vertex.GetSubtreeSum();
    }

    public int GetSubtreeSum() {
        return subtree_sum;
    }
}
