using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Plant : MonoBehaviour
{
<<<<<<< HEAD:FrontEnd/Michelin/Assets/Scripts/Plant.cs
    private bool isPlayerEnter = false; // 플레이어가 채집 반경 내에 들어왔는지 확인
    private PlantSpawnManager spawnManager; // SpawnManager
=======
    private GameMgr gameMgr;

    public SOItem soItem;
>>>>>>> BEDev:FrontEnd/Michelin/Assets/Scripts/Field/Plant.cs

    [HideInInspector]
    public int spawnPointId; // 해당 채집품이 스폰된 SpawnPoint의 Id

<<<<<<< HEAD:FrontEnd/Michelin/Assets/Scripts/Plant.cs
    void Awake() {
        spawnManager = GameObject.Find("PlantSpawnManager").GetComponent<PlantSpawnManager>();
    }

    void Update() {
        // 플레이어가 채집 반경내에 있고 && 채집 버튼(C)를 누르면 채집
        if (isPlayerEnter && Input.GetKeyDown(KeyCode.C)) {
            spawnManager.Gathering(spawnPointId);
            Destroy(gameObject);
        }
    }

    private void OnTriggerEnter2D(Collider2D other) {
        if (other.CompareTag("Player")) {
            isPlayerEnter = true;
        }    
    }

    private void OnTriggerExit2D(Collider2D other) {
        if (other.CompareTag("Player")) {
            isPlayerEnter = false;
        }    
=======
    private void Awake() {
        gameMgr = GameMgr.Instance;
    }

    public void Get() {
        PlantSpawnManager spawnManager 
            = GameObject.Find(gameMgr.currentField)
                .transform
                .Find("PlantSpawnManager")
                .GetComponent<PlantSpawnManager>();
        spawnManager.Gathering(spawnPointId);
        Destroy(gameObject);
>>>>>>> BEDev:FrontEnd/Michelin/Assets/Scripts/Field/Plant.cs
    }
}
