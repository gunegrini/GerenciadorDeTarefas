# Gerenciador de Tarefas

Este é um aplicativo de Gerenciamento de Tarefas desenvolvido em Java usando a biblioteca Swing para a interface gráfica. Ele permite adicionar, editar, concluir e excluir tarefas, além de salvar e carregar tarefas de um arquivo.

## Funcionalidades

- **Adicionar Tarefa:** Permite adicionar novas tarefas à lista.
- **Editar Tarefa:** Permite editar a descrição de uma tarefa existente.
- **Concluir Tarefa:** Permite marcar uma tarefa como concluída.
- **Excluir Tarefa:** Permite remover uma tarefa da lista.
- **Salvar Tarefas:** As tarefas são salvas automaticamente em um arquivo (`tasks.ser`) após qualquer alteração.
- **Carregar Tarefas:** As tarefas são carregadas automaticamente do arquivo (`tasks.ser`) ao iniciar o aplicativo.

## Estrutura do Projeto

O projeto contém os seguintes componentes principais:

- **TaskManagerGUI:** A classe principal que define a interface gráfica e gerencia as ações do usuário.
- **Task:** Uma classe que representa uma tarefa com descrição e status de conclusão.
