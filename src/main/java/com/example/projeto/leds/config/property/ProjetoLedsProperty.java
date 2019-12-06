package com.example.projeto.leds.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("projetoleds")
public class ProjetoLedsProperty {
	
	private String origemPermitida = "http://localhost:8000";
	
	public String getOrigemPermitida() {
		return origemPermitida;
	}

	public void setOrigemPermitida(String origemPermitida) {
		this.origemPermitida = origemPermitida;
	}



	private final Seguranca seguranca = new Seguranca();
	
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
