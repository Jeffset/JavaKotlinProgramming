package com.lab2.SumsOnTrees;

import javax.inject.Inject;

public class Vertex5Impl1 implements Vertex5 {

    private int subtree_sum;
    private int vertex_number;

    @Inject
    public Vertex5Impl1() {
        vertex_number = 5;
        subtree_sum = vertex_number;
    }

    @Override
    public int GetSubtreeSum() {
        return subtree_sum;
    }
}