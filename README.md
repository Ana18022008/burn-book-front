<div align="center">
  <img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" />
  <img src="https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white" />
  <img src="https://img.shields.io/badge/JPA-59666C?style=for-the-badge" />
  <img src="https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=hibernate&logoColor=white" />
</div>



  <div align="center">
  
# 📖 Burn Book App

*"Onde os segredos não são guardados...são usados."*

</div>

O **Burn Book** é uma aplicação Android moderna focada em interações sociais e desabafos, inspirada em uma estética marcante e funcionalidade fluida. O projeto utiliza as tecnologias mais recentes do ecossistema Android para garantir performance e uma interface de usuário (UI) adaptativa.

---

##  Funcionalidades

| Funcionalidade | Descrição |
| --- | --- |
| **Feed de Publicações** | Visualize o que a comunidade está pensando em tempo real. |
| **Sistema de Comentários** | Interaja com as postagens através de uma interface de árvore de comentários intuitiva. |
| **Modo Escuro (Dark Mode)** | Suporte nativo para troca dinâmica de temas, garantindo conforto visual em qualquer ambiente. |
| **Design Edge-to-Edge** | Interface que aproveita 100% da tela, com barras de sistema transparentes e cores vibrantes. |
| **Integração com API** | Consumo de dados assíncronos utilizando Retrofit e Coroutines. |
| **Persistência de Dados** | Uso de DataStore para salvar preferências do usuário. |

---

##  Stack Tecnológica

O desenvolvimento do Burn Book foi pautado no uso das ferramentas mais atuais e recomendadas pelo Google para o ecossistema Android:

* **Linguagem:** Kotlin
* **UI Framework:** Jetpack Compose (Declarative UI)
* **Arquitetura:** MVVM (Model-View-ViewModel)
* **Navegação:** Compose Navigation
* **Rede:** Retrofit 2 & OkHttp 4
* **Gerenciamento de Versões:** Gradle Version Catalog (`libs.versions.toml`)

---

##  Identidade Visual

O projeto utiliza uma paleta de cores personalizada e uma tipografia única para proporcionar uma experiência de leitura agradável e imersiva.

* **Rosa Burn:** `#F65B75` (Cor Primária)
* **Tipografia:** Fontes personalizadas como *Just Me*, *Jaques Shadow*, *Inter* e *Cinzel*.

---

##  Como Executar o Projeto

Para rodar o Burn Book localmente em sua máquina, siga os passos abaixo:

1. **Clone o repositório:**
   ```bash
   git clone https://github.com/Ana18022008/burn-book-front.git
   ```

2. **Abra no Android Studio:**
   Certifique-se de estar usando a versão **Iguana (2023.2.1)** ou superior para compatibilidade total com o Gradle 8.3.

3. **Sincronize o Gradle:**
   O projeto utiliza o novo sistema de `libs.versions.toml`. Deixe o Android Studio baixar todas as dependências automaticamente.

4. **Execute:**
   Selecione um emulador ou dispositivo físico com **Android 8.0 (API 26)** ou superior e clique em *Run*.

---

## 📂 Estrutura de Pastas (Principais)

A arquitetura do projeto foi pensada para ser escalável e de fácil manutenção:

```plaintext
com.example.burnbook/
 ├── api/             # Definições de endpoints e chamadas externas

 ├── datastore/       # Persistência local (preferências do usuário)

 ├── model/           # Data Classes (Models e Responses)

 ├── network/         # Configuração do Retrofit e interceptors

 ├── repository/      # Camada de abstração de dados (API + Local)

 ├── ui.theme/        # Definições de cores, fontes e tema do sistema

 ├── viewmodel/       # Lógica de interface e gerenciamento de estados

 ├── MainActivity.kt  # Ponto de entrada e configuração do NavHost

 └── Pages/           # Telas (Screens) principais do fluxo Compose
```

---

##  Licença

Este projeto está sob a licença **MIT**. Veja o arquivo `LICENSE` para mais detalhes.

---

##  Desenvolvido por

* Ana Ribeiro
* Emanuelle Hostin
* Hugo Deleon

