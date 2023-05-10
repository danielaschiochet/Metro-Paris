package it.polito.tdp.metroparis.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import it.polito.tdp.metroparis.db.MetroDAO;

public class Model {
	
	private Graph<Fermata, DefaultEdge> grafo;
	private List<Fermata> fermate;
	private Map<Integer, Fermata> fermateIdMap; //la creiamo per aiutare il dao
	
	public void creaGrafo() {
		
		//crea l'oggetto grafo
		this.grafo = new SimpleGraph<>(DefaultEdge.class);
		
		//aggiungi i vertici
		MetroDAO dao = new MetroDAO();
		fermate = dao.readFermate();
		
		fermateIdMap = new HashMap<>();
		for(Fermata f: this.fermate) {
			this.fermateIdMap.put(f.getIdFermata(), f);
		}
		
		Graphs.addAllVertices(grafo, fermate);
		
		//aggiungi gli archi
	/*	//metodo 1: considero tutti i potenziali archi -meno veloce
		for(Fermata partenza: this.grafo.vertexSet()) {
			for(Fermata arrivo: this.grafo.vertexSet()) {
				if(dao.isConnesse(partenza,arrivo)) {
					this.grafo.addEdge(partenza, arrivo);
				}
			}
		}
	*/
		
	/*	//metodo 2: data una fermata, trova la lista di quelle adiacenti
		for(Fermata partenza: this.grafo.vertexSet()) {
			List<Fermata> collegate = dao.trovaCollegate(partenza);
			for(Fermata arrivo: collegate) {
				this.grafo.addEdge(partenza, arrivo);
			}
		}
		
		//metodo 2a: data una fermata, troviamo la lista di id connessi (con idMap) 
		for(Fermata partenza: this.grafo.vertexSet()) {
			List<Fermata> collegate = dao.trovaIdCollegate(partenza, this.fermateIdMap);
			for(Fermata arrivo: collegate) {
				this.grafo.addEdge(partenza, arrivo);
			}
		}
	*/
		
		//metodo 3: faccio una query per prendermi tutti gli edges (con classe di appoggio coppieF e idMap) -più veloce
		List<CoppieF> coppie = dao.getAllCoppie(fermateIdMap);
		for(CoppieF c: coppie) {
			grafo.addEdge(c.getP(), c.getA());
		}
		
		System.out.println("Grafo creato con "+this.grafo.vertexSet().size()+" vertici e "+grafo.edgeSet().size()+" archi.");
		System.out.println(grafo);
	}
	
	/*determina il percorso minimo tra 2 fermate*/
	public List<Fermata> percorso(Fermata p, Fermata a) {
		//Visita il grafo partendo da 'p'
		BreadthFirstIterator<Fermata, DefaultEdge> visita = new BreadthFirstIterator<>(this.grafo, p);
		List<Fermata> raggiungibili = new ArrayList<Fermata>();
		while(visita.hasNext()) {
			Fermata f = visita.next();
			//raggiungibili.add(f);
		}
		//System.out.println(raggiungibili);
		
		//Trova il percorso sull'albero di visita
		//1. creo una lista di vertici che sarà il mio percorso
		List<Fermata> percorso = new ArrayList<Fermata>();
		//2.inizializzo corrente all'arrivo
		Fermata corrente = a;
		//3. metto nella lista l'arrivo
		percorso.add(a);
		
		//4. data corrente trovo l'arco
		DefaultEdge e = visita.getSpanningTreeEdge(corrente);
		while(e!=null) {
			//5. dato l'arco trovo il vertici precedente e lo aggiungo al cammino
			Fermata precedente = Graphs.getOppositeVertex(this.grafo, e, corrente);
			percorso.add(0,precedente);
			//6. ripeto mettendo corrente = precedente
			corrente = precedente;
			
			e = visita.getSpanningTreeEdge(corrente);
		}
		return percorso;
	}

	public List<Fermata> getAllFermate(){
		MetroDAO dao = new MetroDAO();
		return dao.readFermate();
	}
	
	public boolean isGrafoLoaded() {
		return this.grafo.vertexSet().size()>0;
	}
}
