package com.Spider;

import cn.edu.hfut.dmic.webcollector.crawler.BreadthCrawler;
import cn.edu.hfut.dmic.webcollector.model.Links;
import cn.edu.hfut.dmic.webcollector.model.Page;

/**
 * Project Name:IceWeather
 * Author:IceChen
 * Date:2016/11/21
 * Notes:
 */

public class NewSpider extends BreadthCrawler {
  /**
   * @param crawlPath 维护URL信息的文件夹，如果爬虫需要断点爬取，每次请选择相同的crawlPath
   * @param autoParse 是否自动抽取符合正则的链接并加入后续任务
   */
  public NewSpider(String crawlPath, boolean autoParse) {
    super(crawlPath, autoParse);
  }

  @Override
  public void visit(Page page, Links nextLinks) {
    page.getUrl();

  }
}
