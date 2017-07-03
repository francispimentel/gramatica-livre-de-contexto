package com.francis.glc;

import org.junit.Assert;
import org.junit.Test;

public class GramaticaTests {

	@Test
	public void testeBasico() {
		Gramatica g = new Gramatica('S');
		g.adicionarVariavel('B');
		g.adicionarVariavel('C');
		g.adicionarVariavel('D');
		g.adicionarTerminais('a');
		g.adicionarTerminais('b');
		g.adicionarTerminais('d');
		g.adicionarProducao('S', "aB");
		g.adicionarProducao('B', "b");
		g.adicionarProducao('B', "C");
		g.adicionarProducao('C', "BDB");
		g.adicionarProducao('D', "d");
		g.adicionarProducao('D', "");

		Assert.assertEquals(g.toString(),
				"G = ({S, B, C, D}, {a, b, d}, P, S)\n\nP = {S -> aB\nB -> b | C\nC -> BDB\nD -> d | Ø}");
		Assert.assertEquals(MinimizadorGramatica.minimizar(g).toString(),
				"G = ({S, B, D}, {a, b, d}, P, S)\n\nP = {S -> aB\nB -> b | BDB | BB\nD -> d}");
		Assert.assertEquals(FNCGramatica.fnc(g).toString(),
				"G = ({S, B, D, A, C}, {a, b, d}, P, S)\n\nP = {S -> AB\nB -> b | CB | BB\nD -> d\nA -> a\nC -> BD}");
		Assert.assertEquals(FNGGramatica.fng(g).toString(),
				"G = ({S, B, A, C}, {a, b, d}, P, S)\n\nP = {S -> aB\nB -> b | bA | bC\nA -> dB | dBA\nC -> b | bA | bC | bAC | bCC}");
	}
}
