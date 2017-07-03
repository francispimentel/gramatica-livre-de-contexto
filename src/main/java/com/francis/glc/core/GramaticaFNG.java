package com.francis.glc.core;

import java.util.ArrayList;
import java.util.List;

public class GramaticaFNG extends GramaticaMinimizada {

	public GramaticaFNG(Gramatica gramatica) {
		super(gramatica);
		reordenarColocandoRecursivasNoFim();
		removerProducoesQueIniciamComVariaveisAnteriores();
		criarAuxiliaresParaRecursivas();
		while (trocarVariaveisAEsquerdaPorTerminais());
		removerVariaveisImpossiveisDeSeChegar();
	}

	private void reordenarColocandoRecursivasNoFim() {
		List<Variavel> aux = new ArrayList<Variavel>();
		for (int i = 0; i < variaveis.size(); i++) {
			Variavel v = variaveis.get(i);
			if (v.temRecursaoAEsquerda()) {
				aux.add(v);
				variaveis.remove(i);
				i--;
			}
		}
		variaveis.addAll(aux);
	}

	private void removerProducoesQueIniciamComVariaveisAnteriores() {
		// Ja pula a primeira
		for (int i = 1; i < variaveis.size(); i++) {
			Variavel v = variaveis.get(i);
			List<String> novasProds = new ArrayList<String>();
			for (int prodIndex = 0; prodIndex < v.getProducoes().size(); prodIndex++) {
				String prod = v.getProducoes().get(prodIndex);
				Character c = prod.charAt(0);
				for (int j = 0; j < i; j++) {
					Variavel v2 = variaveis.get(j);
					if (v2.getNome().equals(c)) {
						v.getProducoes().remove(prodIndex);
						prodIndex--;
						for (String prodsV2 : v2.getProducoes()) {
							novasProds.add(prod.replaceFirst(c.toString(), prodsV2));
						}
						break;
					}
				}
			}
			for (String s : novasProds) {
				v.adicionarProducao(s);
			}
		}
	}

	private void criarAuxiliaresParaRecursivas() {
		List<Variavel> variaveisAuxiliares = new ArrayList<Variavel>();
		for (Variavel v : variaveis) {
			if (v.temRecursaoAEsquerda()) {
				List<String> novasProducoes = new ArrayList<String>();
				for (String s : v.getRecursoesAEsquerda()) {
					Variavel aux = criarAuxiliarParaRecursao(variaveisAuxiliares, s.substring(1));
					for (String prod : v.getProducoes()) {
						if (!prod.startsWith(v.getNome().toString())) {
							String novaProducao = prod + aux.getNome();
							novasProducoes.add(novaProducao);
						}
					}
				}
				for (String s : v.getRecursoesAEsquerda()) {
					v.removerProducao(s);
				}
				for (String s : novasProducoes) {
					v.adicionarProducaoSemValidarVariavel(s);
				}
			}
		}
		variaveis.addAll(variaveisAuxiliares);
	}

	private boolean trocarVariaveisAEsquerdaPorTerminais() {
		boolean houveSubstituicoes = false;
		for (Variavel v : variaveis) {
			List<String> novasProducoes = new ArrayList<String>();
			for (int i = 0; i < v.getProducoes().size(); i++) {
				String prod = v.getProducoes().get(i);
				if (Character.isUpperCase(prod.charAt(0))) {
					v.getProducoes().remove(i);
					i--;
					houveSubstituicoes = true;
					Variavel substituicao = buscarVariavel(prod.charAt(0));
					prod = prod.substring(1);
					for (String subProd : substituicao.getProducoes()) {
						novasProducoes.add(subProd + prod);
					}

				}
			}
			for (String s : novasProducoes) {
				v.adicionarProducao(s);
			}
		}
		return houveSubstituicoes;
	}

	private Variavel criarAuxiliarParaRecursao(List<Variavel> variaveisAuxiliares, String producaoRecursiva) {
		char letra;
		outer: for (letra = 'A'; letra <= 'Z'; letra++) {
			if (buscarVariavel(letra) == null) {
				for (Variavel v : variaveisAuxiliares) {
					if (v.getNome().equals(letra)) {
						continue outer;
					}
				}
				break;
			}
		}
		Variavel v = new Variavel(this, letra);
		v.adicionarProducao(producaoRecursiva);
		v.adicionarProducaoSemValidarVariavel(producaoRecursiva + v.getNome());
		variaveisAuxiliares.add(v);
		return v;
	}
}