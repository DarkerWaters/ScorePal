import 'package:flutter/material.dart';
import 'package:multiphone/helpers/values.dart';
import 'package:multiphone/providers/active_match.dart';
import 'package:multiphone/providers/sport.dart';
import 'package:multiphone/widgets/select_sport_widget.dart';
import 'package:multiphone/widgets/setup_badminton_widget.dart';
import 'package:multiphone/widgets/setup_ping_pong.dart';
import 'package:multiphone/widgets/tennis/setup_tennis_widget.dart';
import 'package:multiphone/widgets/side_drawer_widget.dart';
import 'package:provider/provider.dart';

class PlayMatchScreen extends StatefulWidget {
  static const String routeName = '/play-match';

  PlayMatchScreen();

  @override
  _PlayMatchScreenState createState() => _PlayMatchScreenState();
}

class _PlayMatchScreenState extends State<PlayMatchScreen> {
  final GlobalKey<ScaffoldState> _scaffoldKey = GlobalKey();

  Widget _createActiveMatchSetup(BuildContext context, ActiveMatch match) {
    var values = Values(context);
    switch (match.sport.id) {
      case SportType.TENNIS:
        return SetupTennisWidget();
      case SportType.BADMINTON:
        return SetupBadmintonWidget();
      case SportType.PING_PONG:
        return SetupPingPongWidget();
    }
    // if error - just return something bad!
    return Text(values.construct(
      values.strings.error_sport_not_found,
      [match.sport.title(context)],
    ));
  }

  @override
  Widget build(BuildContext context) {
    // get the values to get strings from
    var values = Values(context);
    // and return the scaffold
    return Scaffold(
      key: _scaffoldKey,
      appBar: AppBar(
        title: Text(values.strings.title_play_match),
        leading: IconButton(
            onPressed: () => _scaffoldKey.currentState.openDrawer(),
            icon: Icon(Icons.more_vert)),
      ),
      drawer: SideDrawer(
          menuItems: MenuItem.mainMenuItems(context),
          currentPath: PlayMatchScreen.routeName),
      body: SingleChildScrollView(
        child: Column(
          children: [
            const SizedBox(
              width: double.infinity,
              height: Values.default_space,
            ),
            SelectSportWidget(),
            const SizedBox(
              width: double.infinity,
              height: Values.default_space,
            ),
            // listen to changes to the sports to show the currently selected sport
            Consumer<ActiveMatch>(
              builder: (ctx, activeMatch, child) {
                // create the correct widget to setup the sport here then
                return _createActiveMatchSetup(ctx, activeMatch);
              },
            )
          ],
        ),
      ),
    );
  }
}
