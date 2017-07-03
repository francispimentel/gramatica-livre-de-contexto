package com.francis.glc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

public class MinimizadorGramatica {
	
	public static Gramatica g;
	
	public static Gramatica minimizar(Gramatica gramatica) {
		g = gramatica.clone();
		removerProducoesVazias();
		removerProducoesUnitarias();
		while (removerVariaveisInuteis());
		return g;
	}
	
	private static void removerProducoesVazias() {
		List<Variavel> fechoVazio = new ArrayList<>();
		
		for(Variavel v : g.variaveis) {
			if (v.temProducao("")) {
				fechoVazio.add(v);
				v.removerProducao("");
			}
		}
		
		boolean continuar;
		do {
			continuar = false;
			
			for(Variavel v : g.variaveis) {
				if(fechoVazio.contains(v)) {
					continue;
				}
				for(Variavel temVazio : fechoVazio) {
					if (v.temProducao(temVazio.toString())) {
						fechoVazio.add(v);
						continuar = true;
					}
				}
			}			
		} while (continuar);		
		
		for(Variavel v : g.variaveis) {
			v.substituirProducoesVazias(fechoVazio);
		}
	}
	
	private static void removerProducoesUnitarias() {
		for(Variavel v : g.variaveis) {
			v.substituirProducoesUnitarias(g.variaveis);
		}
	}
	
	/**
	 * Retorna true se encontrou simbolos inuteis
	 * @return
	 */
	private static boolean removerVariaveisInuteis() {
		return removerVariaveisImpossiveisDeSeChegar() || removerVariaveisQueNaoLevamATerminais();
	}
	
	
	
	/**
	 * Retorna true se removeu alguem
	 * @return
	 */
	static boolean removerVariaveisImpossiveisDeSeChegar() {
		boolean encontrouInutil = false;
		Set<Character> uteis = new HashSet<Character>();
		encontrarVariaveisPossiveisDeChegarRecursivamente(uteis, g.inicial);
		for(int i = 0; i < g.variaveis.size(); i++) {
			Variavel v = g.variaveis.get(i);
			if(!uteis.contains(v.getNome())) {
				g.variaveis.remove(v);
				i--;
				encontrouInutil = true;
			}
		}
		return encontrouInutil;
	}

	private static void encontrarVariaveisPossiveisDeChegarRecursivamente(Set<Character> uteis, Variavel raiz) {
		uteis.add(raiz.getNome());
		
		for(String prod : raiz.getProducoes()) {
			for(Character c : prod.toCharArray()) {
				Variavel v = g.buscarVariavel(c);
				if(v != null) {
					if(!uteis.contains(v.getNome())) {
						encontrarVariaveisPossiveisDeChegarRecursivamente(uteis, v);
					}
				}
			}
		}
	}	
	
	/**
	 * Retorna true se removeu alguem
	 * @return
	 */
	private static boolean removerVariaveisQueNaoLevamATerminais() {
		Set<Character> uteis = new HashSet<Character>();		
		verificarRecursivamenteSeChegaATerminal(uteis, "[a-z]*");
		
		boolean removeAlguem = false;
		
		for(int i = 0; i < g.variaveis.size(); i++) {
			Variavel v = g.variaveis.get(i);
			if(!uteis.contains(v.getNome())) {
				removeAlguem = true;
				g.variaveis.remove(v);
				i--;
				for(Variavel v2 : g.variaveis) {
					for(int j = 0; j < v2.getProducoes().size(); j++) {
						String prod = v2.getProducoes().get(j);
						if(prod.contains(v.getNome().toString())) {
							v2.getProducoes().remove(j);
							j--;
						}
					}
				}
			}
		}
		
		return removeAlguem;
	}
	
	/**
	 * Retorna as variaveis que levam a terminais
	 * @param uteis
	 * @param regex
	 * @return
	 */
	private static void verificarRecursivamenteSeChegaATerminal(Set<Character> uteis, String regex) {
		Pattern p = Pattern.compile(regex);
		boolean continuar = false;
		outer:
		for(Variavel v : g.variaveis) {
			if(uteis.contains(v.getNome())) {
				continue;
			}
			for(String prod : v.getProducoes()) {
				if(p.matcher(prod).matches()) {
					uteis.add(v.getNome());
					continuar = true;
					continue outer;
				}
			}
		}
		if(continuar) {
			StringBuffer buffer = new StringBuffer("[a-z,");
			for(Character c : uteis) {
				buffer.append(c + ",");
			}
			buffer.append("]*");
			verificarRecursivamenteSeChegaATerminal(uteis, buffer.toString());
		}
	}
}