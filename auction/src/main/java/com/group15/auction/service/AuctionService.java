package com.group15.auction.service;

import com.group15.auction.bean.ItemBean;
import com.group15.auction.model.Auction;
import com.group15.auction.model.Bid;
import com.group15.auction.repository.AuctionRepository;
import com.group15.auction.repository.BidRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class AuctionService {
    protected final Environment env;
    protected final RestTemplate restTemplate;
    protected final AuctionRepository auctionRepo;
    protected final BidRepository bidRepo;

    public AuctionService(Environment env, RestTemplateBuilder restTemplateBuilder, AuctionRepository auctionRepo, BidRepository bidRepo) {
        this.env = env;
        this.restTemplate = restTemplateBuilder.build();
        this.auctionRepo = auctionRepo;
        this.bidRepo = bidRepo;
    }

    public JSONArray getAllAuctions() {
        List<Auction> auctions = auctionRepo.findAll();
        JSONArray returnedAuctions = new JSONArray();

        Integer[] itm_ids = new Integer[auctions.size()];
        for (int i = 0; i < auctions.size(); i++) {
            Auction auction = auctions.get(i);
            itm_ids[i] = auction.getAuc_itm_id();
        }

        List<ItemBean> itemBeans = getItemsByIds(itm_ids);

        for (int i = 0; i < auctions.size(); i++) {
            JSONObject auction = new JSONObject(auctions.get(i));
            auction.put("auc_itm_id", new JSONObject(itemBeans.get(i)));

            returnedAuctions.put(auction);
        }

        return returnedAuctions;
    }

    public JSONArray getAuctionsByKey(String keyword) {
        List<ItemBean> itemBeans = getItemsByKey(keyword);

        Integer[] itm_ids = new Integer[itemBeans.size()];
        for (int i = 0; i < itemBeans.size(); i++) {
            itm_ids[i] = itemBeans.get(i).getItm_id();
        }

        List<Auction> auctions = auctionRepo.findByItemId(itm_ids);
        JSONArray returnedAuctions = new JSONArray();

        for (int i = 0; i < auctions.size(); i++) {
            JSONObject auction = new JSONObject(auctions.get(i));
            auction.put("auc_itm_id", new JSONObject(itemBeans.get(i)));

            returnedAuctions.put(auction);
        }

        return returnedAuctions;
    }

    public JSONObject getAuctionJSON(Integer auc_id) {

        if(auctionRepo.findById(auc_id).isPresent()) {
            Auction auction = auctionRepo.findById(auc_id).get();
            JSONObject auctionJSON = new JSONObject(auction);

            ItemBean itemBean = getItem(auction.getAuc_itm_id());
            auctionJSON.put("auc_itm_id", new JSONObject(itemBean));

            return auctionJSON;
        } else {
            return new JSONObject();
        }
    }

    public Auction getAuction(Integer auc_id) {
        if(auctionRepo.findById(auc_id).isPresent()) {
            return auctionRepo.findById(auc_id).get();
        } else {
            return null;
        }
    }

    public List<Bid> getAllBids() {
        return bidRepo.findAll();
    }

    public List<Bid> getAuctionBids(Integer auc_id) {
        return bidRepo.findByAuction(auc_id);
    }

    public Bid getAuctionBestBid(Integer auc_id) {
        return bidRepo.findBestByAuction(auc_id);
    }

    public void resetAuctionData() {
        auctionRepo.resetAuctionData();
    }

    public static record PostItem(
            Integer itm_id
    ) {}
    private ItemBean getItem(Integer itm_id) {
        String url = "http://localhost:" + env.getProperty("itemServer.port") + "/api/items/get-item";

        PostItem itemPayload = new PostItem(itm_id);
        HttpEntity<PostItem> request = new HttpEntity<>(itemPayload);

        ResponseEntity<ItemBean> response = this.restTemplate.postForEntity(url, request, ItemBean.class); //ToDO: Try catch

        return response.getBody();
    }

    public static record PostItems(
            Integer[] itm_ids
    ) {}
    private List<ItemBean> getItemsByIds(Integer[] itm_ids) {
        String url = "http://localhost:" + env.getProperty("itemServer.port") + "/api/items/get-items-by-ids";

        PostItems itemsPayload = new PostItems(itm_ids);
        HttpEntity<PostItems> request = new HttpEntity<>(itemsPayload);

        ResponseEntity<ItemBean[]> response = this.restTemplate.postForEntity(url, request, ItemBean[].class); //ToDO: Try catch

        return Arrays.stream(response.getBody()).toList();
    }

    public static record PostItemsKey(
            String keyword
    ) {}
    private List<ItemBean> getItemsByKey(String keyword) {
        String url = "http://localhost:" + env.getProperty("itemServer.port") + "/api/items/get-items-by-key";

        PostItemsKey itemsPayload = new PostItemsKey(keyword);
        HttpEntity<PostItemsKey> request = new HttpEntity<>(itemsPayload);

        ResponseEntity<ItemBean[]> response = this.restTemplate.postForEntity(url, request, ItemBean[].class); //ToDO: Try catch

        return Arrays.stream(response.getBody()).toList();
    }

}
