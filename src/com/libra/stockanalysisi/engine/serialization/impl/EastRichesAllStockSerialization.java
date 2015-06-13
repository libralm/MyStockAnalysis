package com.libra.stockanalysisi.engine.serialization.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.libra.stockanalysisi.bean.BaseStock;
import com.libra.stockanalysisi.engine.serialization.IAllStockSerialization;

public class EastRichesAllStockSerialization implements IAllStockSerialization{

	@Override
	public BaseStock[] deserializationStockInfo(String pData) {
		// TODO Auto-generated method stub
		List<BaseStock> list = new ArrayList<BaseStock>();
		Document document;
		try {
			document = Jsoup.connect(pData).get();
			Element element = document.select("div.quotebody").first();
			Elements hrefEelement = element.select("a[target]");
			int size = hrefEelement.size();
			for(int i =0; i<size; i++){
				Element aElement = hrefEelement.get(i);
				String url = aElement.attr("href");
				String text = aElement.text();
				String gid = url.split(".html")[0].split("http://quote.eastmoney.com/")[1];
				String name = text.split("\\(")[0];
				BaseStock stock = new BaseStock();
				stock.setGid(gid);
				stock.setName(name);
				list.add(stock);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list.toArray(new BaseStock[list.size()]);
	}

}
