import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: MyHomePage(),
    );
  }
}

class MyHomePage extends StatefulWidget {
  MyHomePage({Key key, this.title}) : super(key: key);

  final String title;

  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  static const channel = MethodChannel('exemplo.flutter.dev/geral');
  String _batteryLevel = 'Desconhecido';
  String _networkType = 'Desconhecido';
  int valorBateria = 0;

  Future<void> _getNetworkType() async {
    String rede = '';
    try {
      String valor = await channel.invokeMethod('getNetworkDetails');
      rede = 'Tipo de Rede: $valor';
    } on PlatformException catch (e) {
      rede = 'Erro ao obter o tipo de rede ' + e.message;
    }

    setState(() {
      _networkType = rede;
    });
  }

  Future<void> _getBatteryLevel() async {
    String bateriaNivel = '';
    try {
      int valor = await channel.invokeMethod('getBatteryLevel');
      bateriaNivel = '$valor';
      valorBateria = valor;
    } on PlatformException catch (e) {
      bateriaNivel = 'Erro ao obter o nível da bateria ' + e.message;
    }

    setState(() {
      _batteryLevel = bateriaNivel;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Native Channel'),
        centerTitle: true,
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            Text(
              _networkType,
              style: Theme.of(context).textTheme.headline5,
            ),
            Row(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                Text('Nivel da Bateria: ',
                    style: Theme.of(context).textTheme.headline5),
                Container(
                  height: 15,
                  width: 35,
                  child: LinearProgressIndicator(
                    value: (valorBateria / 100),
                    backgroundColor: Color.fromRGBO(209, 224, 224, 0.2),
                    valueColor: AlwaysStoppedAnimation(Colors.green),
                  ),
                ),
                Text(
                  ' $_batteryLevel%',
                  style: TextStyle(fontSize: 15),
                ),

              ],
            ),
            SizedBox(height: 250),
            Text('Clique para acionar a função nativa'),
            Icon(Icons.arrow_circle_down),
          ],

        ),
      ),
      floatingActionButtonLocation:
          FloatingActionButtonLocation.miniCenterFloat,
      floatingActionButton: FloatingActionButton(
        onPressed: () {
          _getBatteryLevel();
          _getNetworkType();
        },
        tooltip: 'Increment',
        child: Icon(Icons.add),
      ),
    );
  }
}
