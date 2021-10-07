package com.lab2.SumsOnTrees;

import javax.inject.Inject;

public class Vertex4 {

    private int subtree_sum;
    private int vertex_number;

    @Inject
    public Vertex4() {
        vertex_number = 5;
        subtree_sum = vertex_number;
    }

    public int GetSubtreeSum() {
        return subtree_sum;
    }
}
