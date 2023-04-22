package sptech.school.asyncrequests;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AsyncRequestsApplication {

	// POST - Cadastro de usuário / conta
	// PUT - Editar dados do usuário
	// POST - Upload foto, documento
	// POST - Transferir
	// POST - Depositar
	// POST - Pagar
	// GET - Extrato
	// GET - Visualizar item (local)
	// GET - Download PDF - Comprovante, Extrato, Relatório geral
	// GET - Excel relatório geral

	public static void main(String[] args) {
		SpringApplication.run(AsyncRequestsApplication.class, args);
	}

}
