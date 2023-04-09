import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:my_flutter/util/app_constants.dart';


class Messages extends Translations {
  @override
  Map<String, Map<String, String>> get keys => {
    'en':AppConstants.en,
    'ar':AppConstants.ar
  };
}