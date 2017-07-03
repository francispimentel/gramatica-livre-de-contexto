package com.francis.glc;

/**
 * Usada apenas para mostrar mensagens user friendly na tela
 * No caso de estourar erros da gramática
 * @author Francis
 *
 */
public class GramaticaException extends RuntimeException {
	
	private static final long serialVersionUID = 7873849941831281288L; 

	public GramaticaException(String message) {
		super(message);
	}
}