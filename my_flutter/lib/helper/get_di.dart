import 'dart:convert';
import 'package:device_info_plus/device_info_plus.dart';
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

import '../data/repository/kyc_verify_repo.dart';


void init() async {
  // Core
  final sharedPreferences = await SharedPreferences.getInstance();
  final BaseDeviceInfo _deviceInfo =  await DeviceInfoPlugin().deviceInfo;
  Get.lazyPut(() => sharedPreferences);
  Get.lazyPut(() => _deviceInfo);
  String _uniqueId;
  try {
    _uniqueId = await  UniqueIdentifier.serial;
  } catch(error) {
    print('error is : $error');
  }

  Get.lazyPut(() => _uniqueId);

  Get.lazyPut(() => ApiClient(
    appBaseUrl: AppConstants.BASE_URL,
    sharedPreferences: Get.find(),
    uniqueId: Get.find(),
    deiceInfo: Get.find(),
  ));

  // Repository
  Get.lazyPut(() => SplashRepo(sharedPreferences: Get.find(), apiClient: Get.find()));
  Get.lazyPut(() => LanguageRepo());
  Get.lazyPut(() => TransactionRepo(apiClient: Get.find(), sharedPreferences: Get.find()));
  Get.lazyPut(() => AuthRepo(apiClient: Get.find(),sharedPreferences: Get.find()));
  Get.lazyPut(() => ProfileRepo(apiClient: Get.find()));
  Get.lazyPut(() => ProfileRepo(apiClient: Get.find()));
  Get.lazyPut(() => WebsiteLinkRepo(apiClient: Get.find()));
  Get.lazyPut(() => BannerRepo(apiClient: Get.find()));
  Get.lazyPut(() => AddMoneyRepo(apiClient: Get.find()));
  Get.lazyPut(() => FaqRepo(apiClient: Get.find()));
  Get.lazyPut(() => NotificationRepo(apiClient: Get.find()));
  Get.lazyPut(() => RequestedMoneyRepo(apiClient: Get.find()));
  Get.lazyPut(() => TransactionHistoryRepo(apiClient: Get.find()));
  Get.lazyPut(() => KycVerifyRepo(apiClient: Get.find()));

  // Controller
  Get.lazyPut(() => ThemeController());
  Get.lazyPut(() => SplashController(splashRepo: Get.find()));
  Get.lazyPut(() => LocalizationController());
  Get.lazyPut(() => LanguageController(sharedPreferences: Get.find()));
  Get.lazyPut(() => TransactionMoneyController(transactionRepo: Get.find(), authRepo: Get.find()));
  Get.lazyPut(() => AddMoneyController(addMoneyRepo:Get.find() ));
  Get.lazyPut(() => NotificationController(notificationRepo: Get.find()));
  Get.lazyPut(() => ProfileController(profileRepo: Get.find()));
  Get.lazyPut(() => FaqController(faqrepo: Get.find()));
  Get.lazyPut(() => BottomSliderController());

  Get.lazyPut(() => MenuController());
  Get.lazyPut(() => AuthController(authRepo: Get.find()));
  Get.lazyPut(() => HomeController());
  Get.lazyPut(() => CreateAccountController());
  Get.lazyPut(() => VerificationController(authRepo: Get.find()));
  Get.lazyPut(() => CameraScreenController());
  Get.lazyPut(() => ForgetPassController());
  Get.lazyPut(() => WebsiteLinkController(websiteLinkRepo: Get.find()));
  Get.lazyPut(() => QrCodeScannerController());
  Get.lazyPut(() => BannerController(bannerRepo: Get.find()));
  Get.lazyPut(() => TransactionHistoryController(transactionHistoryRepo: Get.find()));
  Get.lazyPut(() => EditProfileController(authRepo: Get.find()));
  Get.lazyPut(() => RequestedMoneyController(requestedMoneyRepo: Get.find()));
  Get.lazyPut(() => ScreenShootWidgetController());
  Get.lazyPut(() => KycVerifyController(kycVerifyRepo: Get.find()));



 
}
