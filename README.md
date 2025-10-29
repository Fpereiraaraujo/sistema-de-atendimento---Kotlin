🏥 DoctorApp — Aplicativo de Gestão de Consultas Médicas
📱 Visão Geral

O DoctorApp é um aplicativo Android desenvolvido em Kotlin utilizando Jetpack Compose e Room, que permite que médicos gerenciem suas disponibilidades e visualizem informações relacionadas às suas consultas de forma simples e moderna.

O foco do sistema é a experiência do médico, oferecendo telas intuitivas e um design baseado em Material 3, com uso de arquitetura modular e persistência local.

🚀 Funcionalidades Principais
👨‍⚕️ Login e Cadastro de Médico

Cadastro com nome, CRM, telefone, especialidade e descrição.

Login validado localmente via banco Room.

Após o login, o médico é redirecionado ao Painel principal (Dashboard).

🗓️ Painel do Médico (Dashboard)

Exibe informações do médico logado.

Mostra especialidade, CRM e telefone.

Botão para acessar a tela de disponibilidades.

Interface moderna com Cards e tipografia do Material Theme 3.

⏰ Tela de Disponibilidade

Permite ao médico adicionar horários de atendimento.

O médico informa:

Dia (formato automático DD/MM — exemplo: digitar 1102 vira 11/02);

Hora de início e fim (formato automático HH:MM — exemplo: digitar 0830 vira 08:30);

Exibição dos horários já cadastrados em uma lista com LazyColumn.

Regras de negócio aplicadas:

Impede o cadastro de datas anteriores ao dia atual.

Mostra alerta amigável se o formato ou data forem inválidos.

Dados são salvos localmente no banco de dados via Room.

🧱 Estrutura do Projeto
com.example.clinica/
├── data/
│   ├── AppDatabase.kt           # Configuração do banco de dados Room
│   ├── DoctorDao.kt             # DAO para operações de médico
│   └── TimeSlotDao.kt           # DAO para horários de disponibilidade
│
├── model/
│   ├── Doctor.kt                # Entidade do médico
│   └── TimeSlotEntity.kt        # Entidade dos horários
│
├── screens/
│   ├── doctor/
│   │   └── DoctorHomeScreen.kt  # Tela principal do médico
│   ├── AvailabilityScreen.kt    # Tela de disponibilidades
│   ├── DoctorLoginScreen.kt     # Tela de login
│   ├── DoctorRegisterScreen.kt  # Tela de cadastro
│   └── DashboardScreen.kt       # Painel principal do médico
│
├── ui/
│   ├── login/WelcomeScreen.kt   # Tela inicial (escolha de login)
│   └── login/theme/             # Tema Material 3
│
└── MainActivity.kt              # Controle de navegação e rotas

🧭 Navegação (Jetpack Navigation Compose)

A navegação é feita com NavHost dentro da MainActivity, que define as rotas:

Rota	Tela	Descrição
doctor_login	DoctorLoginScreen	Login de médicos
doctor_register	DoctorRegisterScreen	Cadastro de médicos
doctor_home/{doctorId}	DoctorHomeScreen	Tela inicial do médico
doctor_availability/{doctorId}	AvailabilityScreen	Tela para definir horários
🧩 Tecnologias Utilizadas
Categoria	Tecnologias
Linguagem	Kotlin
UI	Jetpack Compose, Material 3
Banco de Dados	Room (SQLite)
Navegação	Navigation Compose
Corrotinas	Kotlin Coroutines, Dispatchers.IO
Persistência	DAO + Entidades Room
Arquitetura	MVVM simplificado (ViewModel + Repository, opcional)
⚙️ Como Executar o Projeto
✅ Pré-requisitos

Android Studio Ladybug (ou superior)

SDK Android 33+

Gradle 8+

Kotlin 1.9+

▶️ Passos

Clone o repositório:

git clone https://github.com/Fpereiraaraujo/sistema-de-atendimento---Kotlin
.git


Abra no Android Studio.

Aguarde o Gradle sincronizar.

Execute o app em um emulador ou dispositivo físico.

💡 Melhorias Futuras

Algumas ideias para evoluir o projeto:

Integração com API real (Firebase / Supabase);

Tela para visualização dos pacientes que marcaram horário;

Sistema de notificações para novas consultas;

Tela de edição e exclusão de horários;

Autenticação via token JWT ou OAuth;

Dashboard com estatísticas de atendimentos.
