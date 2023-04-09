
import 'package:flutter/material.dart';
import 'package:my_flutter/util/images.dart';

class CustomLogo extends StatelessWidget {
  final double height,width;
  CustomLogo({
    this.height,this.width
  });

  @override
  Widget build(BuildContext context) {
    return Container(
      height: height,
      width: width,
      child: Image.asset(Images.logo),

    );
  }
}
