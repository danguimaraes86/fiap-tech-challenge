## Objetivo

Neste desaﬁo, vamos desenvolver um sistema de e-commerce que permita aos usuários realizarem as seguintes operações:

1. [x] Login e Registro de Usuário: Os usuários devem ser capazes de se cadastrar e fazer login no sistema usando as
   ferramentas do Spring Security para autenticação e autorização.
2. [x] Gestão de Itens: Os usuários administradores terão acesso a uma tela de gestão de itens, basicamente o controle
   de cadastro e manutenção de itens, bem como seus preços.
3. [x] Carrinho de Compras: Os usuários podem adicionar e remover itens do carrinho de compras. O carrinho de compras
   deve ser persistente e associado ao usuário logado.
4. [ ] Pagamentos (Simulação): Implementar uma tela que simule o processo de pagamento, onde os usuários possam
   visualizar os itens do carrinho e concluir uma compra ﬁctícia. Não é necessário integrar com formas de pagamento
   reais, apenas uma simulação.

## Requisitos Técnicos

1. [x] Utilize o framework Spring Boot para criar o sistema.
2. [x] Utilize o Spring Security para implementar o controle de autenticação e autorização.
3. [x] Implemente a arquitetura de microsserviços para as funcionalidades de login, gestão de itens, gestão de preços,
   carrinho de compras e pagamento. Cada funcionalidade deve ser um microsserviço separado.
4. [x] Utilize um banco de dados para armazenar informações de usuários, itens, preços e carrinhos de compras. Você pode
   escolher o banco de dados de sua preferência.
5. [x] Forneça uma documentação adequada do sistema, incluindo instruções de instalação e uso.
6. [x] O sistema deve ser capaz de lidar com sessões de usuário e manter o estado do carrinho de compras entre as
   sessões.
7. [x] Implemente validações adequadas para garantir a segurança e a integridade dos dados.
8. [x] Garanta a segurança dos endpoints dos microsserviços usando o Spring Security.

## Avaliação

A avaliação se dará com base nos seguintes critérios:

1. Funcionalidade: O sistema deve atender a todos os requisitos funcionais especiﬁcados.
2. Segurança: O uso adequado do Spring Security e práticas de segurança é essencial.
3. Arquitetura de Microsserviços: A implementação de microsserviços deve ser clara e eﬁciente.
4. Qualidade do Código: O código deve ser organizado, legível e seguir as boas práticas de programação.
5. Documentação: A documentação do sistema deve ser completa e fácil de entender.
6. Usabilidade: A interface do usuário deve ser amigável e intuitiva.
