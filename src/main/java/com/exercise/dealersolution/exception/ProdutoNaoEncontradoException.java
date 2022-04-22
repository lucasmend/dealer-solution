package com.exercise.dealersolution.exception;

public class ProdutoNaoEncontradoException extends RuntimeException{

  public ProdutoNaoEncontradoException() {
    super("Produto não encontrado");
  }
}
