
import 'package:expandable_bottom_sheet/expandable_bottom_sheet.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:my_flutter/controller/banner_controller.dart';
import 'package:my_flutter/controller/home_controller.dart';
import 'package:my_flutter/controller/notification_controller.dart';
import 'package:my_flutter/controller/profile_screen_controller.dart';
import 'package:my_flutter/controller/requested_money_controller.dart';
import 'package:my_flutter/controller/splash_controller.dart';
import 'package:my_flutter/controller/transaction_controller.dart';
import 'package:my_flutter/controller/transaction_history_controller.dart';
import 'package:my_flutter/controller/websitelink_controller.dart';
import 'package:my_flutter/util/dimensions.dart';
import 'package:my_flutter/view/screens/home/widget/app_bar_base.dart';
import 'package:my_flutter/view/screens/home/widget/bottom_sheet/expandable_contant.dart';
import 'package:my_flutter/view/screens/home/widget/bottom_sheet/persistent_header.dart';
import 'package:my_flutter/view/screens/home/widget/first_card_portion.dart';
import 'package:my_flutter/view/screens/home/widget/linked_website.dart';
import 'package:my_flutter/view/screens/home/widget/secend_card_portion.dart';
import 'package:my_flutter/view/screens/home/widget/shimmer/web_site_shimmer.dart';
import 'package:my_flutter/view/screens/home/widget/third_card_portion.dart';
import 'package:shared_preferences/shared_preferences.dart';


class HomeScreen extends StatefulWidget {
  const HomeScreen({Key key}) : super(key: key);
  @override
  State<HomeScreen> createState() => _HomeScreenState();
}

class _HomeScreenState extends State<HomeScreen> {
  bool isFirst = true;
  Future<void> _loadData(BuildContext context, bool reload) async {
  var shared = await SharedPreferences.getInstance();
    Get.put(shared);
    Get.find<ProfileController>().profileData(reload: reload);
    Get.find<BannerController>().getBannerList(reload);
    Get.find<RequestedMoneyController>().getRequestedMoneyList(1 ,reload: reload );
    Get.find<RequestedMoneyController>().getOwnRequestedMoneyList(1 ,reload: reload );
    Get.find<TransactionHistoryController>().getTransactionData(1, reload: reload);
    Get.find<WebsiteLinkController>().getWebsiteList();
    Get.find<NotificationController>().getNotificationList();
    Get.find<TransactionMoneyController>().getPurposeList();
    Get.find<TransactionMoneyController>().fetchContact();
    Get.find<TransactionMoneyController>().getWithdrawMethods(isReload: reload);
    Get.find<RequestedMoneyController>().getWithdrawHistoryList();




  }
  @override
  void initState() {

    _loadData(context, false);
    isFirst = false;
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return  GetBuilder<HomeController>(
        builder: (controller) {
          return Scaffold(
            appBar: AppBarBase(),
            body: ExpandableBottomSheet(
                enableToggle: true,
                background: RefreshIndicator(
                  onRefresh: () async {
                    await _loadData(context, true);
                  },
                  child: SingleChildScrollView(
                    physics: AlwaysScrollableScrollPhysics(),
                    child: GetBuilder<SplashController>(builder: (splashController) {
                        return Column(
                          children: [
                            splashController.configModel.themeIndex == '1'
                                ? GetBuilder<ProfileController>(builder: (profile)=> FirstCardPortion())
                                : splashController.configModel.themeIndex == '2' ? SecondCardPortion() : splashController.configModel.themeIndex == '3' ? ThirdCardPortion() :
                            GetBuilder<ProfileController>(builder: (profile)=> FirstCardPortion()),

                            SizedBox(height: Dimensions.PADDING_SIZE_DEFAULT),

                            GetBuilder<WebsiteLinkController>(builder: (websiteLinkController){
                              return websiteLinkController.isLoading ? WebSiteShimmer() :
                              websiteLinkController.websiteList.length > 0 ?  LinkedWebsite() : SizedBox();
                            }),
                            const SizedBox(height: 80),
                          ],
                        );
                      }
                    ),
                  ),
                ),
                persistentContentHeight: 70,
                persistentHeader: CustomPersistentHeader(),
                expandableContent: CustomExpandableContant()
            ),
          );
        });
  }

}

