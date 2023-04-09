import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:my_flutter/util/app_constants.dart';
import 'package:my_flutter/util/dimensions.dart';
import 'package:my_flutter/util/styles.dart';

class DemoOtpHint extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return AppConstants.DEMO ? Padding(
      padding: const EdgeInsets.symmetric(vertical: Dimensions.PADDING_SIZE_SMALL),
      child: Text(
        'for_demo_1234'.tr,
        style: rubikMedium.copyWith(fontSize: Dimensions.FONT_SIZE_SMALL, color: Theme.of(context).highlightColor),
      ),
    ) : SizedBox();
  }
}
