package com.francis.glc.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GramaticaFNC extends GramaticaMinimizada {

	public GramaticaFNC(Gramatica gramatica) {
		super(gramatica);
		removerProducoesComTerminais();
		removerProducoesComMaisDeDuasVariaveis();
		removerVariaveisImpossiveisDeSeChegar();
	}

	private void removerProducoesComTerminais() {
		List<String> producoesParaModificar = new ArrayList<String>();
		for (Variavel v : variaveis) {
			v.buscarProducoesForaDaFNC(producoesParaModificar);
		}

		List<Variavel> variaveisAuxiliares = new ArrayList<Variavel>();
		Map<String, String> trocas = new HashMap<String, String>();

		for (String prod : producoesParaModificar) {
			for (Character c : prod.toCharArray()) {
				if (terminais.contains(c)) {
					criarVariavelAuxiliar(variaveisAuxiliares, c.toString(), trocas);
				}
			}
		}

		for (Variavel v : variaveis) {
			v.trocarProducoes(trocas);
		}

		variaveis.addAll(variaveisAuxiliares);
	}

	private void removerProducoesComMaisDeDuasVariaveis() {
		List<String> producoesParaModificar = new ArrayList<String>();
		for (Variavel v : variaveis) {
			v.buscarProducoesForaDaFNC(producoesParaModificar);
		}

		List<Variavel> variaveisAuxiliares = new ArrayList<Variavel>();
		Map<String, String> trocas = new HashMap<String, String>();

		Pattern patternDuasVariaveis = Pattern.compile("[A-Z]{2}");
		for (String prod : producoesParaModificar) {
			Matcher matcher = patternDuasVariaveis.matcher(prod);
			while (matcher.find()) {
				criarVariavelAuxiliar(variaveisAuxiliares, matcher.group(), trocas);
			}
		}

		for (Variavel v : variaveis) {
			v.trocarProducoes(trocas);
		}

		variaveis.addAll(variaveisAuxiliares);
	}

	private void criarVariavelAuxiliar(List<Variavel> variaveisAuxiliares, String producao,
			Map<String, String> trocas) {
		for (Variavel v : variaveisAuxiliares) {
			if (v.temProducao(producao)) {
				return;
			}
		}
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
		v.adicionarProducao(producao);
		variaveisAuxiliares.add(v);
		trocas.put(producao, v.getNome().toString());
	}
}