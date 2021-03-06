import 'package:flutter/material.dart';
import 'package:multiphone/helpers/values.dart';
import 'package:multiphone/match/match_setup.dart';
import 'package:multiphone/providers/active_match.dart';
import 'package:multiphone/widgets/select_item_list_widget.dart';
import 'package:multiphone/widgets/select_item_widget.dart';
import 'package:provider/provider.dart';

class SelectSinglesDoublesWidget extends SelectItemListWidget {
  const SelectSinglesDoublesWidget({
    Key key,
  }) : super(
          key: key,
          itemSize: Values.select_item_size_medium,
        );

  @override
  List<SelectItemWidget> items(BuildContext context) {
    final values = Values(context);
    return [
      SelectItemWidget(
        icon: Icons.one_k,
        text: values.strings.tennis_singles,
        iconSize: Values.image_medium,
      ),
      SelectItemWidget(
        icon: Icons.five_k,
        text: values.strings.tennis_doubles,
        iconSize: Values.image_medium,
      ),
    ];
  }

  @override
  int getInitialSelection(BuildContext context) {
    // the initial selection is handled by the active match's setup
    var setup = Provider.of<ActiveMatch>(context, listen: false).setup;
    switch (setup.singlesDoubles) {
      case SINGLES_DOUBLES.SINGLES:
        return 0;
      case SINGLES_DOUBLES.DOUBLES:
        return 1;
    }
    return 0;
  }

  @override
  void onSelectionChanged(BuildContext context, int newSelection) {
    // the user just selected which number of SinglesDoubles to play
    var setup = Provider.of<ActiveMatch>(context, listen: false).setup;
    switch (newSelection) {
      case 0:
        setup.singlesDoubles = SINGLES_DOUBLES.SINGLES;
        break;
      case 1:
        setup.singlesDoubles = SINGLES_DOUBLES.DOUBLES;
        break;
    }
  }
}
