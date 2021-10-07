package com.lab2.SumsOnTrees;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class Vertex2 {

    private int subtree_sum;
    private int vertex_number;
    private int checking_singleton;

    @Inject
    public Vertex2(Vertex4 first_vertex, Vertex5 second_vertex) {
        vertex_number = 5;
        subtree_sum = vertex_number + first_vertex.GetSubtreeSum() +
                second_vertex.GetSubtreeSum();
    }

    public int GetSubtreeSum() {
        return subtree_sum;
    }

    public void UpdateCheckingSingleton() {
        checking_singleton++;
    }

    public int GetCheckingSingleton() {
        return checking_singleton;
    }
}
