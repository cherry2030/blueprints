package com.tinkerpop.blueprints.pgm;

import com.tinkerpop.blueprints.BaseTest;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class IndexTestSuite extends ModelTestSuite {

    public IndexTestSuite() {
    }

    public IndexTestSuite(final SuiteConfiguration config) {
        super(config);
    }

    public void testPutGetRemoveVertex(final IndexableGraph graph) {
        if (config.supportsVertexIndex && !config.isRDFModel) {
            this.stopWatch();
            Index<Vertex> index = graph.createIndex("basic", Vertex.class, Index.Type.MANUAL);
            BaseTest.printPerformance(graph.toString(), 1, "manual index created", this.stopWatch());
            Vertex v1 = graph.addVertex(null);
            Vertex v2 = graph.addVertex(null);
            if (config.supportsVertexIteration)
                assertEquals(count(graph.getVertices()), 2);

            this.stopWatch();
            index.put("dog", "puppy", v1);
            index.put("dog", "mama", v2);
            BaseTest.printPerformance(graph.toString(), 2, "vertices manually index", this.stopWatch());
            assertEquals(v1, index.get("dog", "puppy").iterator().next());
            assertEquals(v2, index.get("dog", "mama").iterator().next());

            v1.removeProperty("dog");
            assertEquals(v1, index.get("dog", "puppy").iterator().next());
            assertEquals(v2, index.get("dog", "mama").iterator().next());

            this.stopWatch();
            graph.removeVertex(v1);
            BaseTest.printPerformance(graph.toString(), 1, "vertex removed and automatically removed from index", this.stopWatch());
            assertEquals(count(index.get("dog", "puppy")), 0);
            assertEquals(v2, index.get("dog", "mama").iterator().next());
            if (config.supportsVertexIteration)
                assertEquals(count(graph.getVertices()), 1);

            v2.setProperty("dog", "mama2");
            assertEquals(v2, index.get("dog", "mama").iterator().next());
            this.stopWatch();
            graph.removeVertex(v2);
            BaseTest.printPerformance(graph.toString(), 1, "vertex removed and automatically removed from index", this.stopWatch());
            assertEquals(count(index.get("dog", "puppy")), 0);
            assertEquals(count(index.get("dog", "mama")), 0);
            if (config.supportsVertexIteration)
                assertEquals(count(graph.getVertices()), 0);
        }
    }

    public void testPutGetRemoveEdge(final IndexableGraph graph) {
        if (config.supportsEdgeIndex && !config.isRDFModel) {
            this.stopWatch();
            Index<Edge> index = graph.createIndex("basic", Edge.class, Index.Type.MANUAL);
            BaseTest.printPerformance(graph.toString(), 1, "manual index created", this.stopWatch());
            Vertex v1 = graph.addVertex(null);
            Vertex v2 = graph.addVertex(null);
            Edge e1 = graph.addEdge(null, v1, v2, "test1");
            Edge e2 = graph.addEdge(null, v1, v2, "test2");
            if (config.supportsEdgeIteration)
                assertEquals(count(graph.getEdges()), 2);

            this.stopWatch();
            index.put("dog", "puppy", e1);
            index.put("dog", "mama", e2);
            BaseTest.printPerformance(graph.toString(), 2, "edges manually index", this.stopWatch());
            assertEquals(e1, index.get("dog", "puppy").iterator().next());
            assertEquals(e2, index.get("dog", "mama").iterator().next());

            v1.removeProperty("dog");
            assertEquals(e1, index.get("dog", "puppy").iterator().next());
            assertEquals(e2, index.get("dog", "mama").iterator().next());

            this.stopWatch();
            graph.removeEdge(e1);
            BaseTest.printPerformance(graph.toString(), 1, "edge removed and automatically removed from index", this.stopWatch());
            assertEquals(count(index.get("dog", "puppy")), 0);
            assertEquals(e2, index.get("dog", "mama").iterator().next());
            if (config.supportsEdgeIteration)
                assertEquals(count(graph.getEdges()), 1);

            v2.setProperty("dog", "mama2");
            assertEquals(e2, index.get("dog", "mama").iterator().next());
            this.stopWatch();
            graph.removeEdge(e2);
            BaseTest.printPerformance(graph.toString(), 1, "edge removed and automatically removed from index", this.stopWatch());
            assertEquals(count(index.get("dog", "puppy")), 0);
            assertEquals(count(index.get("dog", "mama")), 0);
            if (config.supportsEdgeIteration)
                assertEquals(count(graph.getEdges()), 0);
        }
    }

}