package com.sistema.delivery.api.core.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sistema.delivery.api.domain.service.EnvioEmailService;
import com.sistema.delivery.api.infraestrutura.service.email.FakeEnvioEmailService;
import com.sistema.delivery.api.infraestrutura.service.email.SandboxEnvioEmailService;
import com.sistema.delivery.api.infraestrutura.service.email.SmtpEnvioEmailService;

@Configuration
public class EmailConfig {

	@Autowired
	private EmailPropers emailPropers;
	
	@Bean
	public EnvioEmailService envioEmailService()
	{
		switch (emailPropers.getImpl()) { 
        case FAKE:
            return new FakeEnvioEmailService();
        case SMTP:
            return new SmtpEnvioEmailService();
        case SANDBOX:
            return new SandboxEnvioEmailService();
        default: 
            return null; 
	}
	
}
}
