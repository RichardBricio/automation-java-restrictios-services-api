Feature: API V3 - Bloqueio Geral
  Como cliente quero que API V3 retorne a informação de bloqueio geral (anotações negativas) para o CNPJ consultado.
  Caso o documento conste na tabela z00.rs_bloqueio_docto no mainframe, o retorno do bloqueio geral será true
  Caso o documento NÃO conste na tabela z00.rs_bloqueio_docto no mainframe, o retorno do bloqueio geral será false

  @Token
  Scenario: Gerar um novo Bearer token válido
    Given Eu seleciono o APIGEE do ambiente de HOMOLOG
    And Eu uso a rota "/security/iam/v1/client-identities/login"
    When Eu envio a requisição para o novo bearer token usando clientid e secret
    Then Eu salvo o novo Bearer token

  @BloqueioGeral
  Scenario Outline: API V3 - "<descricao>"
    Given Eu executo o caso de teste "<descricao>"
    Given Eu seleciono o APIGEE do ambiente de HOMOLOG
    And Eu uso a rota "/credit-services/business-restrictions/v1/countries/BRA/documents/"
    And Eu uso o documento "<documento>" para minha requisição
    And Eu uso o Bearer token salvo no header
    When Eu envio a requisição GET
    Then Eu capturo a tela com o resultado
    And O Http response precisa ser 200
    And Eu valido o retorno da API <status>

    Examples: 
      | descricao                                          | documento      | status |
      | Documento SEM bloqueio geral (anotações negativas) | 14969127000128 | false  |
      | Documento COM bloqueio geral                       | 34764920000106 | true   |
      | Documento SEM bloqueio geral                       | 00000208000100 | false  |
      | Documento COM bloqueio geral                       | 02255984000187 | true   |
      | Documento SEM bloqueio geral                       | 00000333000110 | false  |
