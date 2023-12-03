import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:flip_card/flip_card.dart';
import 'app_data.dart';
import 'widget_selectable_list.dart';

class LayoutConnected extends StatefulWidget {
  const LayoutConnected({Key? key}) : super(key: key);

  @override
  State<LayoutConnected> createState() => _LayoutConnectedState();
}

class _LayoutConnectedState extends State<LayoutConnected> {
  final ScrollController _scrollController = ScrollController();
  final _messageController = TextEditingController();
  final FocusNode _messageFocusNode = FocusNode();
  List<bool> _hovering = List<bool>.filled(16, false);
  List<GlobalKey<FlipCardState>> cardKeys =
      List.generate(16, (_) => GlobalKey<FlipCardState>());

  int? firstClickedIndex;
  Color? firstClickedColor;

  @override
  void initState() {
    super.initState();
    AppData appData = Provider.of<AppData>(context, listen: false);
    appData.addPlayer(appData.userName);
  }

  @override
  Widget build(BuildContext context) {
    AppData appData = Provider.of<AppData>(context);
    List<bool> clicked = List<bool>.filled(appData.cardColors.length, false);

    return CupertinoPageScaffold(
        navigationBar: const CupertinoNavigationBar(
          middle: Text("Memory"),
        ),
        child: Column(
          children: [
            const SizedBox(height: 52),
            Row(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                const SizedBox(width: 8),
                const Text(
                  "Torn de: ",
                  style: TextStyle(fontSize: 16, fontWeight: FontWeight.w200),
                ),
                const SizedBox(width: 8),
                Text(
                  appData.turn,
                  style: const TextStyle(
                      fontSize: 16, fontWeight: FontWeight.w400),
                ),
                Expanded(child: Container()),
                const Text(
                  "En espera: ",
                  style: TextStyle(fontSize: 16, fontWeight: FontWeight.w200),
                ),
                Text(
                  appData.wait,
                  style: TextStyle(fontSize: 16, fontWeight: FontWeight.w200),
                ),
                SizedBox(
                  width: 140,
                  height: 32,
                  child: CupertinoButton(
                    onPressed: () {
                      appData.disconnectFromServer();
                    },
                    padding: EdgeInsets.zero,
                    child: const Text(
                      "Disconnect",
                      style: TextStyle(fontSize: 14),
                    ),
                  ),
                ),
                const SizedBox(width: 8),
              ],
            ),
            const SizedBox(height: 24),
            Center(
              child: Column(
                children: List.generate(
                  4,
                  (i) => Row(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: List.generate(4, (j) {
                      int index = i * 4 + j;
                      return MouseRegion(
                        onEnter: (_) {
                          setState(() {
                            _hovering[index] = true;
                          });
                        },
                        onExit: (_) {
                          setState(() {
                            _hovering[index] = false;
                          });
                        },
                        child: GestureDetector(
                          onTap: () {
                            print('Card $index tapped');
                            setState(() {
                              // When a card is clicked, update its clicked state
                              appData.flipCard(i, j, appData.userName);
                              print(firstClickedIndex);
                              print(firstClickedColor);
                              print(appData.cardColors[index]);
                            });
                          },
                          child: Card(
                            shape: RoundedRectangleBorder(
                              side: BorderSide(color: Colors.black, width: 2),
                            ),
                            child: AnimatedContainer(
                              duration: Duration(milliseconds: 200),
                              color: _hovering[index]
                                  ? Colors.grey
                                  : appData.cardColors[index] ?? Colors.white,
                              child: Padding(
                                padding: EdgeInsets.all(64.0),
                              ),
                            ),
                          ),
                        ),
                      );
                    }),
                  ),
                ),
              ),
            )
          ],
        ));
  }
}
