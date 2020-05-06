package cs2321;

import net.datastructures.*;

/**
 * Alex Hromada
 * Assignment 7
 * This class is a representation of an Adjacent Map Graph implementation of a Graph ADT structure
 *
 * @param <E>
 */
/*
 * Implement Graph interface. A graph can be declared as either directed or undirected.
 * In the case of an undirected graph, methods outgoingEdges and incomingEdges return the same collection,
 * and outDegree and inDegree return the same value.
 *
 * @author CS2321 Instructor
 */
public class AdjListGraph<V, E> implements Graph<V, E> {

    private boolean isDirected;         // Boolean flag to set the graph to either directed or undirected
    private DoublyLinkedList<Vertex<V>> vertices = new DoublyLinkedList<>();        // List to hold vertices
    private DoublyLinkedList<Edge<E>> edges = new DoublyLinkedList<>();             // List to hold edges

    public AdjListGraph(boolean directed) {
		isDirected = directed;
    }

    public AdjListGraph() {
        isDirected = false;
    }


    /* (non-Javadoc)
     * @see net.datastructures.Graph#edges()
     */
    @TimeComplexity("O(m)")
    public Iterable<Edge<E>> edges() {
        return edges;
    }

    /* (non-Javadoc)
     * @see net.datastructures.Graph#endVertices(net.datastructures.Edge)
     */
    @TimeComplexity("O(1)")
    public Vertex[] endVertices(Edge<E> e) throws IllegalArgumentException {
    	InnerEdge<E> edge = validate(e);
        return edge.getEndpoints();
    }


    /* (non-Javadoc)
     * @see net.datastructures.Graph#insertEdge(net.datastructures.Vertex, net.datastructures.Vertex, java.lang.Object)
     */
    @TimeComplexity("O(1)")
    public Edge<E> insertEdge(Vertex<V> u, Vertex<V> v, E o)
            throws IllegalArgumentException {
        // Inserts a new edge into the list if it does not exist
        if(getEdge(u,v) == null){
        	InnerEdge<E> e = new InnerEdge<E>(o, u, v);
        	e.setPos(edges.addLast(e));
        	InnerVertex<V> origin = validate(u);
			InnerVertex<V> dest = validate(v);
			origin.getOutgoing().put(v, e);
			dest.getIncoming().put(u, e);
			return e;
		} else
			throw new IllegalArgumentException("Edge already exists");
    }

	/* (non-Javadoc)
	 * @see net.datastructures.Graph#removeEdge(net.datastructures.Edge)
	 */
    @TimeComplexity("O(1)")
	public void removeEdge(Edge<E> e) throws IllegalArgumentException {
		InnerEdge<E> edge = validate(e);
		Vertex<V>[] endpoints = edge.getEndpoints();

		InnerVertex<V> vert1 = validate(endpoints[0]);
		InnerVertex<V> vert2 = validate(endpoints[1]);
		vert1.getOutgoing().remove(vert2);
		vert2.getIncoming().remove(vert1);

		edges.remove(edge.getPos());
	}


    /* (non-Javadoc)
     * @see net.datastructures.Graph#insertVertex(java.lang.Object)
     */
    @TimeComplexity("O(1)")
    public Vertex<V> insertVertex(V o) {
    	InnerVertex<V> newVert = new InnerVertex<>(o, isDirected);
    	newVert.setPosition(vertices.addLast(newVert));
        return newVert;
    }

    /* (non-Javadoc)
     * @see net.datastructures.Graph#numEdges()
     */
    @TimeComplexity("O(1)")
    public int numEdges() {
        return edges.size();
    }

    /* (non-Javadoc)
     * @see net.datastructures.Graph#numVertices()
     */
    @TimeComplexity("O(1)")
    public int numVertices() {
        return vertices.size();
    }

    /* (non-Javadoc)
     * @see net.datastructures.Graph#opposite(net.datastructures.Vertex, net.datastructures.Edge)
     */
    public Vertex<V> opposite(Vertex<V> v, Edge<E> e)
            throws IllegalArgumentException {
        InnerEdge<E> edge = validate(e);
        Vertex<V>[] endpoints = edge.getEndpoints();
        if(endpoints[0] == v)
        	return endpoints[1];
        else if(endpoints[1] == v)
        	return endpoints[0];
        else
        	throw new IllegalArgumentException("v is not incident to this edge");
    }


    /* (non-Javadoc)
     * @see net.datastructures.Graph#removeVertex(net.datastructures.Vertex)
     */
    @TimeComplexity("O(deg(v))")
    public void removeVertex(Vertex<V> v) throws IllegalArgumentException {
        InnerVertex<V> vert = validate(v);
		for (Edge<E> e :
				vert.getOutgoing().values()) {
			removeEdge(e);
		}
		for (Edge<E> e :
				vert.getIncoming().values()) {
			removeEdge(e);
		}
		vertices.remove(vert.getPosition());

    }

    /*
     * replace the element in edge object, return the old element
     */
    public E replace(Edge<E> e, E o) throws IllegalArgumentException {
        InnerEdge<E> edge = validate(e);
		Vertex<V>[] endpoints = edge.getEndpoints();
        InnerEdge<E> newEdge = new InnerEdge<E>(o, endpoints[0], endpoints[1]);

        E element = edges.set(edge.getPos(), newEdge).getElement();

        newEdge.setPos(edge.getPos());

        InnerVertex<V> origin = validate(endpoints[0]);
        InnerVertex<V> dest = validate(endpoints[1]);
        origin.getOutgoing().put(endpoints[1], e);
        dest.getIncoming().put(endpoints[0], e);

        return element;

    }

    /*
     * replace the element in vertex object, return the old element
     */
    public V replace(Vertex<V> v, V o) throws IllegalArgumentException {
		InnerVertex<V> vert = validate(v);
		InnerVertex<V> newVert = new InnerVertex<>(o, isDirected);
		Position<Vertex<V>> pos = vert.getPosition();

		V element = vertices.set(pos, newVert).getElement();

		newVert.setPosition(pos);

		return element;
    }

    /* (non-Javadoc)
     * @see net.datastructures.Graph#vertices()
     */
    @TimeComplexity("O(n)")
    public Iterable<Vertex<V>> vertices() {
        return vertices;
    }

    /**
     * Returns the number of outgoing edges
     * @param v
     * @return num of outgoing edges
     * @throws IllegalArgumentException
     */
    @Override
    @TimeComplexity("O(1)")
    public int outDegree(Vertex<V> v) throws IllegalArgumentException {
    	InnerVertex<V> vert = validate(v);
        return vert.getOutgoing().size();
    }

    /**
     * Returns the number of incoming edges
     * @param v
     * @return num of incoming edges
     * @throws IllegalArgumentException
     */
    @Override
    @TimeComplexity("O(1)")
    public int inDegree(Vertex<V> v) throws IllegalArgumentException {
		InnerVertex<V> vert = validate(v);
		return vert.getIncoming().size();
    }

    /**
     * Returns an iterable of the outgoing edges
     * @param v
     * @return
     * @throws IllegalArgumentException
     */
    @Override
    @TimeComplexity("O(deg(v))")
    public Iterable<Edge<E>> outgoingEdges(Vertex<V> v)
            throws IllegalArgumentException {
        InnerVertex<V> vert = validate(v);
        return vert.getOutgoing().values();
    }

    /**
     * Returns an iterable of the incoming edges
     * @param v
     * @return
     * @throws IllegalArgumentException
     */
    @Override
    @TimeComplexity("O(deg(v))")
    public Iterable<Edge<E>> incomingEdges(Vertex<V> v)
            throws IllegalArgumentException {
		InnerVertex<V> vert = validate(v);
		return vert.getIncoming().values();
    }

    /**
     * Returns the edge between two given points
     * @param u
     * @param v
     * @return an edge
     * @throws IllegalArgumentException
     */
    @Override
    @TimeComplexity("O(min(deg(u),deg(v))")
    @TimeComplexityExpected("O(1)")
    public Edge<E> getEdge(Vertex<V> u, Vertex<V> v)
            throws IllegalArgumentException {
		InnerVertex<V> vert = validate(v);
        return vert.getOutgoing().get(v);
    }

    //----------------------------------------------------------------------------------------------------------------
	// Helper/Utility Methods

    /**
     * Check to make sure the given vertex a valid instance of InnerVertex
     * @param u
     * @return casted InnerVertex
     * @throws IllegalArgumentException
     */
    private InnerVertex<V> validate(Vertex<V> u) throws IllegalArgumentException{

    	if (!(u instanceof InnerVertex)){
    		throw new IllegalArgumentException("Vertex is invalid");
		}
    	InnerVertex<V> vertex = (InnerVertex<V>) u;  // Cast vertex

    	return vertex;

	}

    /**
     * Check to make sure the given edge is a valid instance of InnerEdge
     * @param e
     * @return casted InnerEdge
     * @throws IllegalArgumentException
     */
	private InnerEdge<E> validate(Edge<E> e) throws IllegalArgumentException{

		if (!(e instanceof InnerEdge)){
			throw new IllegalArgumentException("Vertex is invalid");
		}
		InnerEdge<E> edge = (InnerEdge<E>) e;  // Cast edge

		return edge;

	}

    /**
     * Private inner InnerVertex class
     * @param <V>
     */
    private class InnerVertex<V> implements Vertex<V> {

        private V element;
        private Position<Vertex<V>> pos;
        private HashMap<Vertex<V>, Edge<E>> outgoing, incoming;

        public InnerVertex(V element, boolean isDirected) {
            this.element = element;
            this.outgoing = new HashMap<>();
            if (isDirected)
                incoming = new HashMap<>();
            else
                incoming = outgoing;
        }

        /**
         * Returns the element associated with the vertex.
         */
        @Override
        public V getElement() {

            return element;
        }

        public void setPosition(Position<Vertex<V>> position) {
            this.pos = position;
        }

        public Position<Vertex<V>> getPosition() {
            return pos;
        }

        public HashMap<Vertex<V>, Edge<E>> getOutgoing() {
            return outgoing;
        }

        public HashMap<Vertex<V>, Edge<E>> getIncoming() {
            return incoming;
        }
    }

    /**
     * Private inner InnerEdge class
     * @param <E>
     */
    private class InnerEdge<E> implements Edge<E> {

        private E element;
        private Position<Edge<E>> pos;
        private Vertex<V>[] endpoints;

        public InnerEdge(E element, Vertex<V> u, Vertex<V> v) {
            this.element = element;
            this.endpoints = (Vertex<V>[]) new Vertex[]{u, v};
        }

        /**
         * Returns the element associated with the edge.
         */
        @Override
        public E getElement() {
            return element;
        }

		public Position<Edge<E>> getPos() {
			return pos;
		}

		public Vertex<V>[] getEndpoints() {
			return endpoints;
		}

		public void setPos(Position<Edge<E>> pos) {
			this.pos = pos;
		}
	}


}
