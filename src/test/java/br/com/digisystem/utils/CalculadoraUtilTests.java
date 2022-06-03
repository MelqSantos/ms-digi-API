package br.com.digisystem.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class) // Indica que é uma classe de teste para ser usado com o Spring
public class CalculadoraUtilTests {
	
	private CalculadoraUtil calculadoraUtil = new CalculadoraUtil();
	
	@Test // Significa que é um método de teste
	void somarTeste() {
		// Temos que ter o valor esperado e o valor atual
		
		int a = 9;
		int b = 5;
		int esperado = 14;
		
		int resultado = calculadoraUtil.soma(a, b);
		
		assertEquals(esperado, resultado);
	}

}
