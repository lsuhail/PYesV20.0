import 'package:device_info_plus/device_info_plus.dart';
import 'package:get/get.dart';
import 'package:my_flutter/controller/auth_controller.dart';
import 'package:my_flutter/controller/language_controller.dart';
import 'package:my_flutter/controller/splash_controller.dart';
import 'package:my_flutter/controller/theme_controller.dart';
import 'package:my_flutter/data/api/api_client.dart';
import 'package:my_flutter/data/repository/auth_repo.dart';
import 'package:my_flutter/data/repository/language_repo.dart';
import 'package:my_flutter/data/repository/splash_repo.dart';
import 'package:my_flutter/util/app_constants.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:unique_identifier/unique_identifier.dart';

class SplashBinding extends Bindings {

  @override
  Future<void> dependencies() async {
  var sharedPreferences =await SharedPreferences.getInstance();
  var serial = await  UniqueIdentifier.serial;
  var deviceInfo=  await DeviceInfoPlugin().deviceInfo;
    Get.lazyPut(()  => SplashController(splashRepo: SplashRepo(sharedPreferences: sharedPreferences, apiClient: ApiClient(  appBaseUrl: AppConstants.BASE_URL,
      sharedPreferences:sharedPreferences,
      uniqueId: serial,
      deiceInfo: deviceInfo,
    ))));
  Get.lazyPut(() => AuthController(authRepo:  AuthRepo(apiClient: ApiClient(
    appBaseUrl: AppConstants.BASE_URL,
    sharedPreferences: sharedPreferences,
    uniqueId: serial,
    deiceInfo: deviceInfo,
  ),sharedPreferences: sharedPreferences)));
   Get.lazyPut(() => LanguageRepo());
   Get.lazyPut(() => LanguageController(  sharedPreferences: sharedPreferences));
  Get.lazyPut(() => ThemeController());


  }
}
