using System.Collections;
using System.Collections.Generic;
using Unity.Collections;
using Unity.VisualScripting;
using UnityEngine;
using static LoginManager;
using UnityEngine.Networking;

public class GameMgr : MonoBehaviour
{
    // instance 멤버변수는 private하게 선언
    private static GameMgr instance = null;
    public string id;
    public string username;
    public string password;
    public string role;
    public string accessToken;
    public int lv;
    public int exp;
    public int money;
    public int hp;
    public int currentArm;
    public int currentVehicle;
    public float time;
    public List<string> learnedList;
    public List<LoginManager.Food> todayMenus;
    public int workers;
    public int onDutyWorkers;
    public List<LoginManager.Food> soldFood;
    public bool noSound;
    public int questSuccess;
    public string lastScene;
    public List<LoginManager.Inventory> inventory;
    public double[] lastXy;
    public string boardId;
    public string replyId;
    public string currentField;
    
    public InventoryControl inventoryControl;

    public class SaveData {
        public int lv;
        public int exp;
        public int money;
        public int hp;
        public int currentArm;
        public int currentVehicle;
        public double time;
        public string[] learnedList;
        public LoginManager.Food[] todayMenus;
        public int workers;
        public int onDutyWorkers;
        public LoginManager.Food[] soldFood;
        public bool noSound;
        public int questSuccess;
        public string lastScene;
        public double[] lastXy;
        public LoginManager.Inventory[] inventory;
        public string currentField;
    }

    private void Awake()
    {
        if (null == instance)
        {
            // 씬 시작될때 인스턴스 초기화, 씬을 넘어갈때도 유지되기위한 처리
            instance = this;
            DontDestroyOnLoad(this.gameObject);

            // inventoryControl = GetComponentInChildren<InventoryControl>();
            // sl = GetComponentInChildren<SaveAndLoad>();
        }
        else
        {
            // instance가, GameManager가 존재한다면 GameObject 제거 
        	Destroy(this.gameObject);
        }
    }

    public static GameMgr Instance
    {
        get
        {
            if (null == instance)
            {
                Debug.Log("오류!오류!오류!!!!!");
                return null;
            }
            return instance;
        }
    }
    // Start is called before the first frame update
    void Start()
    {
        
    }

    public void LoadInventory()
    {
        inventoryControl.LoadMoney(money);
        foreach (var item in inventory)
        {
            string itemCode = item.code;
            int itemIdx = item.idx;
            int itemCount = item.count;
            inventoryControl.LoadToInven(itemIdx, itemCode, itemCount);
        }
    }

    public void SaveGame()
    {
        InventoryUpdate();
        StartCoroutine(API_Save());
    }

    IEnumerator API_Save()
    {
        SaveData saveData = new SaveData();
        saveData.lv = lv;
        saveData.exp = exp;
        saveData.money = money;
        saveData.hp = hp;
        saveData.currentArm = currentArm;
        saveData.currentVehicle = currentVehicle;
        saveData.time = time;
        string[] tempLearnedList = new string[learnedList.Count];
        for (int i = 0; i < tempLearnedList.Length; i++){
            tempLearnedList[i] = learnedList[i];
        }
        saveData.learnedList = tempLearnedList;
        LoginManager.Food[] tempTodayMenus = new LoginManager.Food[todayMenus.Count];
        for (int i = 0; i < tempTodayMenus.Length; i++){
            tempTodayMenus[i] = todayMenus[i];
        }
        saveData.todayMenus = tempTodayMenus;
        saveData.workers = workers;
        saveData.onDutyWorkers = onDutyWorkers;
        LoginManager.Food[] tempSoldFood = new LoginManager.Food[soldFood.Count];
        for (int i = 0; i < tempSoldFood.Length; i++){
            tempSoldFood[i] = soldFood[i];
        }
        saveData.soldFood = tempSoldFood;
        saveData.noSound = noSound;
        saveData.questSuccess = questSuccess;
        saveData.lastScene = lastScene;
        saveData.lastXy = lastXy;
        LoginManager.Inventory[] tempInventory = new LoginManager.Inventory[inventory.Count];
        for (int i = 0; i < tempInventory.Length; i++){
            tempInventory[i] = inventory[i];
        }
        saveData.inventory = tempInventory;
        saveData.currentField = currentField;
        string saveDataJson = JsonUtility.ToJson(saveData);
        byte[] mySaveData = System.Text.Encoding.UTF8.GetBytes(saveDataJson);
        UnityWebRequest saveDataWWW = new UnityWebRequest("https://j10a609.p.ssafy.io/api/users/save", "POST");
        UploadHandlerRaw saveDataUhr = new UploadHandlerRaw(mySaveData);
        saveDataUhr.contentType = "application/json";
        saveDataWWW.SetRequestHeader("Authorization", $"Bearer {accessToken}");
        saveDataWWW.uploadHandler = saveDataUhr;
        saveDataWWW.downloadHandler = new DownloadHandlerBuffer();
        yield return saveDataWWW.SendWebRequest();
        Debug.Log(saveDataWWW.result);
    }

    public void InventoryUpdate()
    {
        money = inventoryControl.money;
        
        inventory.Clear();
        List<ItemSlot[]> slots = inventoryControl.GetSlots();
        for (int index = 0; index < slots.Count; index++)
        {
            for (int i = 0; i < slots[index].Length; i++)
            {
                if (slots[index][i].item != null)
                {
                    Inventory item = new Inventory
                    {
                        idx = i, 
                        code = slots[index][i].item.itemCode, 
                        count = slots[index][i].itemCount 
                    };
                    inventory.Add(item);
                }
            }
        }
    }

    public void Logout() {
        SaveGame();
        Destroy(this.gameObject);
    }

    private void OnApplicationQuit() {
        SaveGame();
        Debug.Log("저장 시도");
    }
}
