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

List<CameraDescription> cameras;

class StartScreen extends StatefulWidget {
  final  Map<String, Map<String, String>> languages;

  const StartScreen({Key key, this.languages}) : super(key: key);

  @override
  _StartScreenState createState() => _StartScreenState(languages);
}

class _StartScreenState extends State<StartScreen> with WidgetsBindingObserver {
final  Map<String, Map<String, String>> _languages;

  _StartScreenState(this._languages);

  @override
  void initState() {
    super.initState();
     setPathUrlStrategy();


        _route().then((v) async {
            done = true;
        }
        );

  }

  SharedPreferences sharedPreferences;
  @override
  void dispose() {
    super.dispose();
  }
  bool done =false;
  Future<void> _route() async {
    await FlutterSmartDialog.init();
   SharedPreferences.getInstance().then((value) async { sharedPreferences  = value;
   await Get.put(sharedPreferences);
    //await Firebase.initializeApp();
    cameras = await availableCameras();


    BaseDeviceInfo _deviceInfo =  await DeviceInfoPlugin().deviceInfo;
    await Get.put(() => sharedPreferences);
    await Get.put(() => _deviceInfo);

    // Core

    String _uniqueId;
    try {
      _uniqueId = await  UniqueIdentifier.serial;
    } catch(error) {
      print('error is : $error');
    }


    await Get.put(() => _uniqueId);

    await Get.put(() => ApiClient(
      appBaseUrl: AppConstants.BASE_URL,
      sharedPreferences: sharedPreferences,
      uniqueId: _uniqueId,
      deiceInfo: _deviceInfo,
    ));
   await Get.put(() => SplashRepo(sharedPreferences: sharedPreferences, apiClient: ApiClient(
      appBaseUrl: AppConstants.BASE_URL,
      sharedPreferences: sharedPreferences,
      uniqueId: _uniqueId,
      deiceInfo: _deviceInfo,
    )));
   await Get.put(() => LanguageRepo());
   await Get.put(() => TransactionRepo(apiClient: ApiClient(
      appBaseUrl: AppConstants.BASE_URL,
      sharedPreferences: sharedPreferences,
      uniqueId: _uniqueId,
      deiceInfo: _deviceInfo,
    ), sharedPreferences: sharedPreferences));
   await Get.put(() => AuthRepo(apiClient: ApiClient(
      appBaseUrl: AppConstants.BASE_URL,
      sharedPreferences: sharedPreferences,
      uniqueId: _uniqueId,
      deiceInfo: _deviceInfo,
    ),sharedPreferences: sharedPreferences));

   await Get.put(() => ProfileRepo(apiClient: ApiClient(
      appBaseUrl: AppConstants.BASE_URL,
      sharedPreferences: sharedPreferences,
      uniqueId: _uniqueId,
      deiceInfo: _deviceInfo,
    )));

   await Get.put(() => WebsiteLinkRepo(apiClient: ApiClient(
      appBaseUrl: AppConstants.BASE_URL,
      sharedPreferences: sharedPreferences,
      uniqueId: _uniqueId,
      deiceInfo: _deviceInfo,
    )));
   await Get.put(() => BannerRepo(apiClient: ApiClient(
      appBaseUrl: AppConstants.BASE_URL,
      sharedPreferences: sharedPreferences,
      uniqueId: _uniqueId,
      deiceInfo: _deviceInfo,
    )));
   await Get.put(() => AddMoneyRepo(apiClient: ApiClient(
      appBaseUrl: AppConstants.BASE_URL,
      sharedPreferences: sharedPreferences,
      uniqueId: _uniqueId,
      deiceInfo: _deviceInfo,
    )));
   await Get.put(() => FaqRepo(apiClient: ApiClient(
      appBaseUrl: AppConstants.BASE_URL,
      sharedPreferences: sharedPreferences,
      uniqueId: _uniqueId,
      deiceInfo: _deviceInfo,
    )));
   await Get.put(() => NotificationRepo(apiClient: ApiClient(
      appBaseUrl: AppConstants.BASE_URL,
      sharedPreferences: sharedPreferences,
      uniqueId: _uniqueId,
      deiceInfo: _deviceInfo,
    )));
   await Get.put(() => RequestedMoneyRepo(apiClient: ApiClient(
      appBaseUrl: AppConstants.BASE_URL,
      sharedPreferences: sharedPreferences,
      uniqueId: _uniqueId,
      deiceInfo: _deviceInfo,
    )));
   await Get.put(() => TransactionHistoryRepo(apiClient: ApiClient(
      appBaseUrl: AppConstants.BASE_URL,
      sharedPreferences: sharedPreferences,
      uniqueId: _uniqueId,
      deiceInfo: _deviceInfo,
    )));
   await Get.put(() => KycVerifyRepo(apiClient: ApiClient(
      appBaseUrl: AppConstants.BASE_URL,
      sharedPreferences: sharedPreferences,
      uniqueId: _uniqueId,
      deiceInfo: _deviceInfo,
    )));
   await Get.put(() => ThemeController( ));
   await Get.put(() => SplashController(splashRepo: SplashRepo(sharedPreferences: sharedPreferences, apiClient: ApiClient(
      appBaseUrl: AppConstants.BASE_URL,
      sharedPreferences: sharedPreferences,
      uniqueId: _uniqueId,
      deiceInfo: _deviceInfo,
    ))));
   await Get.put(() => LocalizationController( ));
   await Get.put(() => LanguageController(  sharedPreferences: sharedPreferences));
   await Get.put(() => TransactionMoneyController(transactionRepo: TransactionRepo(apiClient: ApiClient(
      appBaseUrl: AppConstants.BASE_URL,
      sharedPreferences: sharedPreferences,
      uniqueId: _uniqueId,
      deiceInfo: _deviceInfo,
    ), sharedPreferences: sharedPreferences), authRepo: AuthRepo(apiClient: ApiClient(
      appBaseUrl: AppConstants.BASE_URL,
      sharedPreferences: sharedPreferences,
      uniqueId: _uniqueId,
      deiceInfo: _deviceInfo,
    ),sharedPreferences: sharedPreferences)));
   await Get.put(() => AddMoneyController(addMoneyRepo:AddMoneyRepo(apiClient: ApiClient(
      appBaseUrl: AppConstants.BASE_URL,
      sharedPreferences: sharedPreferences,
      uniqueId: _uniqueId,
      deiceInfo: _deviceInfo,
    )) ));
   await Get.put(() => NotificationController(notificationRepo: NotificationRepo(apiClient: ApiClient(
      appBaseUrl: AppConstants.BASE_URL,
      sharedPreferences: sharedPreferences,
      uniqueId: _uniqueId,
      deiceInfo: _deviceInfo,
    ))));
   await Get.put(() => ProfileController(profileRepo: ProfileRepo(apiClient: ApiClient(
      appBaseUrl: AppConstants.BASE_URL,
      sharedPreferences: sharedPreferences,
      uniqueId: _uniqueId,
      deiceInfo: _deviceInfo,
    ))));
   await Get.put(() => FaqController(faqrepo: FaqRepo(apiClient: ApiClient(
      appBaseUrl: AppConstants.BASE_URL,
      sharedPreferences: sharedPreferences,
      uniqueId: _uniqueId,
      deiceInfo: _deviceInfo,
    ))));
   await Get.put(() => BottomSliderController());

   await Get.put(() => MenuController());
   await Get.put(() => AuthController(authRepo: AuthRepo(apiClient: ApiClient(
      appBaseUrl: AppConstants.BASE_URL,
      sharedPreferences: sharedPreferences,
      uniqueId: _uniqueId,
      deiceInfo: _deviceInfo,
    ),sharedPreferences: sharedPreferences)));
   await Get.put(() => HomeController());
   await Get.put(() => CreateAccountController());
   await Get.put(() => VerificationController(authRepo: AuthRepo(apiClient: ApiClient(
      appBaseUrl: AppConstants.BASE_URL,
      sharedPreferences: sharedPreferences,
      uniqueId: _uniqueId,
      deiceInfo: _deviceInfo,
    ),sharedPreferences: sharedPreferences)));
   await Get.put(() => CameraScreenController());
   await Get.put(() => ForgetPassController());
   await Get.put(() => WebsiteLinkController(websiteLinkRepo:  WebsiteLinkRepo(apiClient: ApiClient(
      appBaseUrl: AppConstants.BASE_URL,
      sharedPreferences: sharedPreferences,
      uniqueId: _uniqueId,
      deiceInfo: _deviceInfo,
    ))));
   await Get.put(() => QrCodeScannerController());
   await Get.put(() => BannerController(bannerRepo:  BannerRepo(apiClient: ApiClient(
      appBaseUrl: AppConstants.BASE_URL,
      sharedPreferences: sharedPreferences,
      uniqueId: _uniqueId,
      deiceInfo: _deviceInfo,
    ))));
   await Get.put(() => TransactionHistoryController(transactionHistoryRepo:  TransactionHistoryRepo(apiClient: ApiClient(
      appBaseUrl: AppConstants.BASE_URL,
      sharedPreferences: sharedPreferences,
      uniqueId: _uniqueId,
      deiceInfo: _deviceInfo,
    ))));
   await Get.put(() => EditProfileController(authRepo:  AuthRepo(apiClient: ApiClient(
      appBaseUrl: AppConstants.BASE_URL,
      sharedPreferences: sharedPreferences,
      uniqueId: _uniqueId,
      deiceInfo: _deviceInfo,
    ),sharedPreferences: sharedPreferences)));
   await Get.put(() => RequestedMoneyController(requestedMoneyRepo: RequestedMoneyRepo(apiClient: ApiClient(
      appBaseUrl: AppConstants.BASE_URL,
      sharedPreferences: sharedPreferences,
      uniqueId: _uniqueId,
      deiceInfo: _deviceInfo,
    ))));
   await Get.put(() => ScreenShootWidgetController());
   await Get.put(() => KycVerifyController(kycVerifyRepo: KycVerifyRepo(apiClient: ApiClient(
      appBaseUrl: AppConstants.BASE_URL,
      sharedPreferences: sharedPreferences,
      uniqueId: _uniqueId,
      deiceInfo: _deviceInfo,
    ))));
   });


  }

  @override
  Widget build(BuildContext context) {
    return GetBuilder<ThemeController>(
      init:   ThemeController(),
        builder: (themeController) {
      return GetBuilder<LocalizationController>(
        init:  LocalizationController(),
          builder: (localizeController) {
    return GetMaterialApp(
      navigatorObservers: [FlutterSmartDialog.observer],
      builder: FlutterSmartDialog.init(),
      locale: localizeController.locale,
      translations: Messages(),
      fallbackLocale: Locale(AppConstants.languages[0].languageCode, AppConstants.languages[0].countryCode),
      theme: themeController.darkTheme?dark:light,
      title: AppConstants.APP_NAME,
      debugShowCheckedModeBanner: false,
      navigatorKey: Get.key,
      initialRoute: RouteHelper.getSplashRoute(),
      getPages: RouteHelper.routes,
      defaultTransition: Transition.topLevel,
      transitionDuration: Duration(milliseconds: 500),
    );
  });});}
}
