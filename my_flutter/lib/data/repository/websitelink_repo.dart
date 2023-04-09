import 'package:flutter/cupertino.dart';
import 'package:get/get_connect/http/src/response/response.dart';
import 'package:my_flutter/data/api/api_client.dart';
import 'package:my_flutter/util/app_constants.dart';

class WebsiteLinkRepo{
  final ApiClient apiClient;

  WebsiteLinkRepo({@required this.apiClient});

  Future<Response> getWebsiteListApi() async {
    return await apiClient.getData(AppConstants.CUSTOMER_LINKED_WEBSITE);
  }
}