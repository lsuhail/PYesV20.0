import 'package:flutter/material.dart';
import 'package:my_flutter/util/color_resources.dart';

ThemeData dark = ThemeData(
  fontFamily: 'Roboto',
  primaryColor: Color(0xca0db4c3),
  brightness: Brightness.dark,
  secondaryHeaderColor: Color(0xffbb811c),
  textTheme: TextTheme(
    titleLarge: TextStyle(color:Color(0xfff39b05)),
    titleSmall: TextStyle(color: Color(0xfff39b05)),
  ),
  bottomNavigationBarTheme: BottomNavigationBarThemeData(backgroundColor: Colors.black, selectedItemColor: ColorResources.themeDarkBackgroundColor),
);
