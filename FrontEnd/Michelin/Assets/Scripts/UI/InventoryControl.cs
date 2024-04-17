using System.Collections;
using System.IO;
using System.Collections.Generic;
using UnityEngine;

// Inventory 정보 type
[System.Serializable]
public class InventoryItem
{
    public string itemCode;
    public int count;
}

[System.Serializable]
public class InventoryContent
{
    public int money;
    public List<InventoryItem> weapon;
    public List<InventoryItem> vehicle;
    public List<InventoryItem> ingredients;
    public List<InventoryItem> recipes;
}


public class InventoryControl : MonoBehaviour
{
    private bool inventroy_show;
    public GameObject Inventory;
    public InventoryContent inventoryDB;
    public InventoryBottom InventoryBottom;

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
    // Json Data 저장
    public static void SaveJsonData(string path, StoreItemDatabase modifiedDB)
    {
        string json = JsonUtility.ToJson(modifiedDB, true);
        File.WriteAllText(path, json);
    }



    // Start is called before the first frame update
    void Start()
    {
        inventroy_show = false;
        inventoryDB = LoadItemJson("inventory.json");
        InventoryBottom.SetBottomScript(inventoryDB.money);
    }

    // Update is called once per frame
    void Update()
    {
        if (Input.GetKeyDown(KeyCode.I))
        {
            inventroy_show = !inventroy_show;
        }
        Inventory.SetActive(inventroy_show);
    }
}
