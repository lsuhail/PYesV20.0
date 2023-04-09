import 'dart:convert';

import 'package:camera/camera.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_smart_dialog/flutter_smart_dialog.dart';
import 'package:get/get_core/src/get_main.dart';
import 'package:get/get_navigation/src/root/get_material_app.dart';
import 'package:get/get_navigation/src/routes/transitions_type.dart';
import 'package:get/get_state_manager/src/simple/get_state.dart';
import 'package:my_flutter/controller/localization_controller.dart';
import 'package:my_flutter/controller/theme_controller.dart';
import 'package:my_flutter/data/model/response/language_model.dart';
import 'package:my_flutter/helper/route_helper.dart';
import 'package:my_flutter/theme/dark_theme.dart';
import 'package:my_flutter/theme/light_theme.dart';
import 'package:my_flutter/util/app_constants.dart';
import 'package:my_flutter/util/images.dart';
import 'package:my_flutter/util/messages.dart';
import 'package:my_flutter/view/screens/splash/start.dart';
import 'package:url_strategy/url_strategy.dart';
import 'dart:async';
import 'package:camera/camera.dart';
import 'package:connectivity_plus/connectivity_plus.dart';
import 'package:flutter_smart_dialog/flutter_smart_dialog.dart';
import 'package:my_flutter/controller/auth_controller.dart';
import 'package:my_flutter/controller/localization_controller.dart';
import 'package:my_flutter/controller/splash_controller.dart';
import 'package:my_flutter/controller/theme_controller.dart';
import 'package:my_flutter/data/api/api_checker.dart';
import 'package:my_flutter/data/model/response/language_model.dart';
import 'package:my_flutter/data/repository/kyc_verify_repo.dart';
import 'package:my_flutter/helper/route_helper.dart';
import 'package:my_flutter/theme/dark_theme.dart';
import 'package:my_flutter/theme/light_theme.dart';
import 'package:my_flutter/util/app_constants.dart';
import 'package:my_flutter/util/images.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:my_flutter/util/messages.dart';
import 'package:my_flutter/view/base/custom_snackbar.dart';
import 'package:my_flutter/helper/get_di.dart' as di;
import 'package:url_strategy/url_strategy.dart';
import 'dart:convert';
import 'package:device_info_plus/device_info_plus.dart';
import 'package:flutter/cupertino.dart';
import 'package:my_flutter/controller/banner_controller.dart';
import 'package:my_flutter/controller/create_account_controller.dart';
import 'package:my_flutter/controller/edit_profile_controller.dart';
import 'package:my_flutter/controller/faq_controller.dart';
import 'package:my_flutter/controller/forget_password_controller.dart';
import 'package:my_flutter/controller/bootom_slider_controller.dart';
import 'package:my_flutter/controller/add_money_controller.dart';
import 'package:my_flutter/controller/kyc_verify_controller.dart';
import 'package:my_flutter/controller/menu_controller.dart';
import 'package:my_flutter/controller/notification_controller.dart';
import 'package:my_flutter/controller/qr_code_scanner_controller.dart';
import 'package:my_flutter/controller/screen_shot_widget_controller.dart';
import 'package:my_flutter/controller/requested_money_controller.dart';
import 'package:my_flutter/controller/camera_screen_controller.dart';
import 'package:my_flutter/controller/home_controller.dart';
import 'package:my_flutter/controller/language_controller.dart';
import 'package:my_flutter/controller/localization_controller.dart';
import 'package:my_flutter/controller/profile_screen_controller.dart';
import 'package:my_flutter/controller/auth_controller.dart';
import 'package:my_flutter/controller/transaction_controller.dart';
import 'package:my_flutter/controller/splash_controller.dart';
import 'package:my_flutter/controller/theme_controller.dart';
import 'package:my_flutter/controller/transaction_history_controller.dart';
import 'package:my_flutter/controller/verification_controller.dart';
import 'package:my_flutter/controller/websitelink_controller.dart';
import 'package:my_flutter/data/api/api_client.dart';
import 'package:my_flutter/data/repository/add_money_repo.dart';
import 'package:my_flutter/data/repository/auth_repo.dart';
import 'package:my_flutter/data/repository/banner_repo.dart';
import 'package:my_flutter/data/repository/faq_repo.dart';
import 'package:my_flutter/data/repository/language_repo.dart';
import 'package:my_flutter/data/repository/notification_repo.dart';
import 'package:my_flutter/data/repository/profile_repo.dart';
import 'package:my_flutter/data/repository/requested_money_repo.dart';
import 'package:my_flutter/data/repository/transaction_repo.dart';
import 'package:my_flutter/data/repository/transaction_history_repo.dart';
import 'package:my_flutter/data/repository/websitelink_repo.dart';
import 'package:my_flutter/data/repository/splash_repo.dart';
import 'package:my_flutter/util/app_constants.dart';
import 'package:my_flutter/data/model/response/language_model.dart';
import 'package:flutter/services.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:get/get.dart';
import 'package:unique_identifier/unique_identifier.dart';
Future<void> main() async {
  setPathUrlStrategy();
  WidgetsFlutterBinding.ensureInitialized();
  cameras = []; //await availableCameras();

 await di.init();

  int _orderID;

  runApp(MyApp());
  SystemChrome.setSystemUIOverlayStyle(SystemUiOverlayStyle(statusBarColor: Colors.transparent.withOpacity(0.3)));

}

class MyApp extends StatelessWidget {
  MyApp();

  @override
  Widget build(BuildContext context) {
    return GetBuilder<ThemeController>(builder: (themeController) {
      return GetBuilder<LocalizationController>(builder: (localizeController) {
        return GetMaterialApp(
          navigatorObservers: [FlutterSmartDialog.observer],
          builder: FlutterSmartDialog.init(),
          title: AppConstants.APP_NAME,
          debugShowCheckedModeBanner: false,
          navigatorKey: Get.key,
          theme: themeController.darkTheme ? dark : light,
          locale: localizeController.locale,
          translations: Messages(),
          fallbackLocale: Locale(AppConstants.languages[0].languageCode, AppConstants.languages[0].countryCode),
          initialRoute: RouteHelper.getSplashRoute(),
          getPages: RouteHelper.routes,
          defaultTransition: Transition.topLevel,
          transitionDuration: Duration(milliseconds: 500),
        );
      },
      );
    },
    );
  }
}

Future<T> invokeNativeMethod<T>(
    String method, [
      dynamic arguments,
    ]) async {

  var platform = MethodChannel('dev.alamalbank.amb.channel/door');
  return await platform.invokeMethod<T>(method, arguments);
}

