import 'package:my_flutter/util/app_constants.dart';
import 'package:flutter/foundation.dart';
import 'package:get/get.dart';
import 'package:shared_preferences/shared_preferences.dart';

class ThemeController extends GetxController {
  ThemeController() {
    _loadCurrentTheme();
  }
  var sharedPreferences;
  bool _darkTheme = false;
  bool get darkTheme => _darkTheme;

  Future<void> toggleTheme() async {
    sharedPreferences  = await SharedPreferences.getInstance();
    _darkTheme = !_darkTheme;

    sharedPreferences.setBool(AppConstants.THEME, _darkTheme);
    update();
  }

  void _loadCurrentTheme() async {
    sharedPreferences  = await SharedPreferences.getInstance();

    _darkTheme = sharedPreferences.getBool(AppConstants.THEME) ?? false;
    update();
  }
}
