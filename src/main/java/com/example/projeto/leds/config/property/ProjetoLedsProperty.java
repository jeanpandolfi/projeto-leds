package com.example.projeto.leds.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("projetoleds")
public class ProjetoLedsProperty {
	/* Configuração da origem permitida para o CORS */
	//private String origemPermitida = "http://172.16.36.6:4200";
	private String origemPermitida = "http://localhost:4200";
	
	private final Seguranca seguranca = new Seguranca();
	
	
	public String getOrigemPermitida() {
		return origemPermitida;
	}

	public void setOrigemPermitida(String origemPermitida) {
		this.origemPermitida = origemPermitida;
	}

	
	public Seguranca getSeguranca() {
		return seguranca;
	}

	public static class Seguranca{
		
		private boolean enableHttps; // por padrão bolleanos são false

		public boolean isEnableHttps() {
			return enableHttps;
		}

		public void setEnableHttps(boolean enableHttps) {
			this.enableHttps = enableHttps;
		}
		
		
	}
}
