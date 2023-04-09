import 'package:flutter/material.dart';
import 'package:my_flutter/data/model/response/language_model.dart';
import 'package:my_flutter/util/app_constants.dart';

class LanguageRepo {
  List<LanguageModel> getAllLanguages({BuildContext context}) {
    return AppConstants.languages;
  }
}
