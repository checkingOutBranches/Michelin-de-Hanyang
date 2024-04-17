using System;
using System.Collections.Generic;
using System.IO;
using UnityEngine;


[System.Serializable]
public class StoreItem
{
    public string itemName;
    public int itemPrice;
    public string itemDescription;
    public bool isPurchased;
}

[System.Serializable]
public class StoreItemDatabase
{ 
    public List<StoreItem> weapon;
    public List<StoreItem> vehicle;
}


// Item 상세 정보 type
[System.Serializable]
public class ItemDict
{
    public string itemName;
    public string itemCode;
    public string itemDescription;
    public string itemImg;
}

// Item 상세정보 리스트
[System.Serializable]
public class ItemInfo
{
    public List<ItemDict> weapon;
    public List<ItemDict> vehicle;
    public List<ItemDict> ingredients;
    public List<ItemDict> recipes;
}


public class ShopManager : MonoBehaviour
{
    public GameObject itemPrefab; // 아이템 Prefab
    public curr_money money_UI; // 보유금액 표시 UI

    public Transform contentParent; // ScrollView의 Content Transform
    public StoreItemDatabase storeDB;
    public InventoryContent inventoryDB;
    public ItemInfo itemInfoDB;

    // Load Json(InventoryContent)
    public static InventoryContent LoadItemJson(string fileName)
    {
        string filePath = Path.Combine(Application.streamingAssetsPath, fileName);
        if (File.Exists(filePath))
        {
            string json = File.ReadAllText(filePath);
            InventoryContent jsonDB = JsonUtility.FromJson<InventoryContent>(json);
            return jsonDB;
        }
        Debug.LogError("Cannot find file!");
        return null;
    }
    // Load Json(Item Info)
    public static ItemInfo LoadInfoJson(string fileName)
    {
        string filePath = Path.Combine(Application.streamingAssetsPath, fileName);
        if (File.Exists(filePath))
        {
            string json = File.ReadAllText(filePath);
            ItemInfo jsonDB = JsonUtility.FromJson<ItemInfo>(json);
            return jsonDB;
        }
        Debug.LogError("Cannot find file!");
        return null;
    }

    // Load Json Data(storeDB)
    public static StoreItemDatabase LoadJsonData(string fileName)
    {
        string filePath = Path.Combine(Application.streamingAssetsPath, fileName);
        if (File.Exists(filePath))
        {
            string json = File.ReadAllText(filePath);
            StoreItemDatabase jsonDB = JsonUtility.FromJson<StoreItemDatabase>(json);
            return jsonDB;
        }
        Debug.LogError("Cannot find file!");
        return null;
    }

    // Json Data 저장
    public static void SaveJsonData(string path, StoreItemDatabase modifiedDB)
    {
        string json = JsonUtility.ToJson(modifiedDB, true);
        File.WriteAllText(path, json);
    }



    void Start()
    {
        //playerInventory

        inventoryDB = LoadItemJson("inventory.json");
        itemInfoDB = LoadInfoJson("itemInfo.json");
        storeDB = LoadJsonData("db.json");

        Debug.Log(storeDB);
        if (storeDB != null)
        {
            foreach (var item in storeDB.weapon)
            {
                GameObject newItem = Instantiate(itemPrefab, contentParent);
                // newItem의 UI 컴포넌트에 item 정보 적용
                ItemUI itemUI = newItem.GetComponent<ItemUI>();
                itemUI.SetItem(item.itemName, item.itemPrice, item.itemDescription, item.isPurchased);
            }
        }

        money_UI.SetMoneyScript(inventoryDB.money);
    }
}
