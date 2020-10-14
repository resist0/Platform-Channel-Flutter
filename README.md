# Platform-Channel
<h4>Fazer chamadas de métodos nativos escritos Kotlin, por meio da linguagem Dart com o conceito do Platform Channel.</h4>

<blockquote>O Flutter nos permite chamar APIs
específicas escritas em código Java ou Kotlin
(Android) ou código Objective C ou Swift (iOS).
A chamadas dos métodos de uma
plataforma específica no Flutter funciona com
a passagem de mensagens.
No aplicativo Flutter, temos que enviar
mensagens para um host em partes iOS ou
Android por meio de um canal de plataforma.
O host escuta no canal e recebe a mensagem,
em seguida, ele invoca o código da linguagem
de programação nativa e envia de volta uma
resposta para a parte Flutter do aplicativo.</blockquote>



Caso deseja implementar um novo metodo você deve seguir os seguintes passos:
- Abrir somente a pasta android pelo android studio.
- Abrir a MainActivity.
- Fazer a declaração do nome do canal (ja implementado).
- Criar um método que acessa o recurso Nativo.
- Sobrescrever o método do Canal.
- Chamar esse método pelo flutter.
