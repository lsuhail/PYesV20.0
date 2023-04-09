import 'dart:async';
import 'package:connectivity_plus/connectivity_plus.dart';
import 'package:device_info_plus/device_info_plus.dart';
import 'package:my_flutter/controller/auth_controller.dart';
import 'package:my_flutter/controller/splash_controller.dart';
import 'package:my_flutter/data/api/api_checker.dart';
import 'package:my_flutter/data/api/api_client.dart';
import 'package:my_flutter/data/repository/splash_repo.dart';
import 'package:my_flutter/helper/route_helper.dart';
import 'package:my_flutter/util/app_constants.dart';
import 'package:my_flutter/util/images.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:my_flutter/view/base/custom_snackbar.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:unique_identifier/unique_identifier.dart';

class SplashScreen extends StatefulWidget {
  @override
  _SplashScreenState createState() => _SplashScreenState();
}

class _SplashScreenState extends State<SplashScreen> with WidgetsBindingObserver {
  StreamSubscription<ConnectivityResult> _onConnectivityChanged;
  Connectivity _connectivity = Connectivity();

  @override
  void initState() {
    super.initState();

    Get.put(() async => SplashController(splashRepo: SplashRepo(sharedPreferences: await SharedPreferences.getInstance(), apiClient: ApiClient(  appBaseUrl: AppConstants.BASE_URL,
      sharedPreferences: await SharedPreferences.getInstance(),
      uniqueId: await  UniqueIdentifier.serial,
      deiceInfo: await DeviceInfoPlugin().deviceInfo,
    ))));
    bool _firstTime = true;
    print('splash screen call');

    _onConnectivityChanged = _connectivity.onConnectivityChanged.listen((ConnectivityResult result) async {
      print('splash screen call 3');
      if(await ApiChecker.isVpnActive()) {
        showCustomSnackBar('you are using vpn', isVpn: true, duration: Duration(minutes: 10));
      }
      if(!_firstTime) {
        print('connection state : $result');
        bool isNotConnected = result != ConnectivityResult.wifi && result != ConnectivityResult.mobile;

        showCustomSnackBar(
          isNotConnected ? 'no_connection'.tr : 'connected'.tr,
          duration: Duration(seconds: isNotConnected ? 6000 : 3),
          isError: isNotConnected,
        );

        if(!isNotConnected) {
          _route();
        }
      }else{
        print('splash screen call 2');

        _route();
      }

    });

  }


  @override
  void dispose() {
    super.dispose();
    _onConnectivityChanged.cancel();
  }

  void _route() {
    Timer(Duration(seconds: 5), () async {


      Get.find<SplashController>().getConfigData().then((value) {
      print('config call ');
      if(value.isOk) {
        Timer(Duration(seconds: 1), () async {
          Get.find<SplashController>().initSharedData().then((value) {


            if(Get
                .find<AuthController>()
                .getCustomerName()
                .isNotEmpty && (Get
                .find<SplashController>()
                .configModel
                .companyName != null)) {
              var iniResult = Get.find<SplashController>().iniResults;
              if(iniResult!=null&&iniResult.containsKey('CLIENT_NAME') &&iniResult.containsKey('COMPANY_NAME_AR')&&iniResult.containsKey('COMPANY_NAME_EN')) {
                Get
                    .find<AuthController>()
                    .setCustomerName(iniResult['CLIENT_NAME']);
                Get
                    .find<AuthController>()
                    .setCustomerNumber(iniResult['PhoneNumber']);
               var configModel=  Get
                    .find<SplashController>()
                    .configModel;
               configModel.languageCode=iniResult["DEFAULT_LANG"];
               configModel.companyName=iniResult["DEFAULT_LANG"]=="ar"?iniResult["APPLICATION_NAME_AR"]:iniResult["APPLICATION_NAME_EN"];

              }
            Get.offNamed(RouteHelper.getLoginRoute(countryCode: Get.find<
                AuthController>().getCustomerCountryCode(),
                phoneNumber: Get.find<AuthController>().getCustomerNumber()));
            }else{
            Get.offNamed(RouteHelper.getChoseLoginRegRoute());}
          });
        });
      }else{
        print(value.isOk);
      }
    });
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: Column(
          mainAxisSize: MainAxisSize.min,
          children: [
            Image.asset(Images.logo, height: 175),
          ],
        ),
      ),
    );
  }
}
