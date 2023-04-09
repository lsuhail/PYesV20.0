import 'package:flutter/material.dart';
import 'package:my_flutter/util/color_resources.dart';

ThemeData light = ThemeData(
  brightness: Brightness.light,
  fontFamily: 'Rubik',
  primaryColor: Color(0xfff39b05),
  secondaryHeaderColor: Color(0xff0db4c3),
  highlightColor: Color(0xffb78021),
  backgroundColor: Colors.white,
  primaryColorDark: Colors.black,
  textTheme: TextTheme(
    titleLarge: TextStyle(color: Color(0xFF343434)),
    titleSmall: TextStyle(color: Color(0xFF25282D)),
  ),
  bottomNavigationBarTheme: BottomNavigationBarThemeData(backgroundColor: Colors.white,selectedItemColor: ColorResources.themeLightBackgroundColor),

);