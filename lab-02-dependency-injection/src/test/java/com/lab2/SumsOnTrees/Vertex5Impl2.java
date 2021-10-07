package com.lab2.SumsOnTrees;

import javax.inject.Inject;

public class Vertex5Impl2 implements Vertex5 {

    private int subtree_sum;
    private int vertex_number;

    @Inject
    public Vertex5Impl2() {
        vertex_number = 0;
        subtree_sum = vertex_number;
    }

    @Override
    public int GetSubtreeSum() {
        return subtree_sum;
    }
}
