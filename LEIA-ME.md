# No Limit Pitch — mod Fabric para Minecraft 26.2

Remove o limite vertical (-90°/+90°) da câmera em primeira pessoa. Depois de
instalado, você pode girar a câmera verticalmente sem parar, passando de
360°/-360° quantas vezes quiser.

## Como funciona

O Minecraft trava o "pitch" (rotação vertical) em `Entity#turn(double, double)`
usando `Mth.clamp(xRot, -90, 90)`. Este mod usa um Mixin para interceptar
exatamente essa chamada e devolver o valor sem cortar. Confirmei os nomes de
classe/método direto no seu `minecraft-26_2-client.jar` (a 26.2 já usa
mapeamento oficial não-ofuscado, então dá pra ler os nomes reais).

## Tecla de atalho

Igual ao mod "Pitchy" (que inspirou essa ideia, mas não tem código público
disponível para versões recentes), este mod tem uma tecla pra ligar/desligar
o giro livre em tempo real — útil se você quiser voltar ao comportamento
normal da câmera por um momento. Por padrão ela vem **sem tecla definida**;
configure em `Opções > Controles > No Limit Pitch`.

## Compatibilidade

Como o mod só mexe em `Entity#turn`, não toca em renderização nem em nada
que Sodium, Lithium, Nvidium, ModernFix, ImmediatelyFast, DynamicFPS,
Vulkanmod, C2ME, etc. também alterem — não deve haver conflito com nenhum
mod da sua lista.

⚠️ Em multiplayer, servidores costumam validar/corrigir a rotação recebida,
então o giro livre pode não "colar" visualmente para outros jogadores ou
pode ser corrigido pelo anti-cheat do servidor. Funciona 100% em singleplayer.

## Pré-requisitos para compilar

- **Java 25 (JDK)** instalado
- Conexão com a internet (o Gradle baixa o Minecraft, o Fabric Loader, a
  Fabric API e o Loom automaticamente na primeira execução)

## Como gerar o .jar

1. Extraia este projeto em uma pasta.
2. Abra um terminal nessa pasta.
3. Rode:

   - Linux/macOS: `./gradlew build`
   - Windows: `gradlew.bat build`

   (Se não vier o `gradlew`, rode antes `gradle wrapper --gradle-version 9.5.1`
   com qualquer Gradle instalado, ou abra a pasta no IntelliJ IDEA, que gera
   o wrapper sozinho.)

4. O `.jar` final aparece em `build/libs/nolimitpitch-1.0.0.jar`.

## Como instalar

1. Instale o **Fabric Loader** (0.19.x+) para Minecraft 26.2.
2. Coloque a **Fabric API** (0.155.2+26.2, a mesma que já está na sua lista)
   na pasta `mods`.
3. Copie `nolimitpitch-1.0.0.jar` para a pasta `mods` também.
4. Abra o jogo normalmente.

## Ajustando se algo não bater

Se ao abrir o jogo o Mixin reclamar de não encontrar a chamada a
`Mth.clamp`, é sinal de que a Mojang mudou ligeiramente o bytecode desse
método em alguma atualização futura. Nesse caso, descompile o
`Entity.class` do seu jar (ex.: com Vineflower/CFR) e ajuste o `target` do
`@Redirect` em `EntityTurnMixin.java` para o novo local.
