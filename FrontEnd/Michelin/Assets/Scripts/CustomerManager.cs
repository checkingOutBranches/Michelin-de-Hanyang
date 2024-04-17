using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.AI;
using UnityEngine.UI; 
using SuperTiled2Unity;

[System.Serializable]
public class CustomerMove {
    public bool Cu_Move;
    public string[] direction;
    [Range(1, 5)]
    public int frequency;
    
}

public class CustomerManager : MonoBehaviour {
    [SerializeField]
    private GameObject npcPrefab; // NPC 프리팹
    [SerializeField]
    // private float moveSpeed = 10f; // 이동 속도
    private Animator animator; // 애니메이터 컴포넌트
    [SerializeField]
    private Sprite redImage; // 빨간색 이미지
    private Vector2 moveDirection = Vector2.zero; // 이동 방향
    private int num = 0;
    public CustomerMove customer;
    // [SerializeField] Transform target;
    private NavMeshAgent agent;
     [SerializeField]
    private List<Transform> targets; // 모든 타겟들을 저장하는 리스트
    private List<bool> targetsActive; // 각 타겟의 활성 상태를 저장
    private bool isBusinessOpen = false; // 영업 상태 변수

    public void StartSpawning() {
         if (isBusinessOpen) { // 영업 중일 때만 NPC 생성
            StartCoroutine(SpawnCustomerRoutine());
        }
    }
    public void StopSpawning() {
        StopAllCoroutines(); // 모든 코루틴 중단 (NPC 생성 중단)
    }

    public void SetBusinessStatus(bool status) {
            isBusinessOpen = status;
            if (isBusinessOpen) {
                StartSpawning();
            } else {
                StopSpawning();
            }
        }

    void Start() {
        animator = GetComponent<Animator>(); // 애니메이터 컴포넌트 할당
        agent = GetComponent<NavMeshAgent>();
        InitializeTargetsActive();
        // StartCoroutine(SpawnCustomerRoutine());
    }


    void InitializeTargetsActive() {
        targetsActive = new List<bool>();  // 이 줄을 추가하여 리스트를 초기화합니다.
        foreach (var target in targets) {
            targetsActive.Add(target.gameObject.activeSelf);
        }
    }
<<<<<<< HEAD
     IEnumerator SpawnCustomerRoutine() {
        while (true) {
            yield return new WaitForSeconds(5); // 4초 간격으로 NPC 스폰
            if (IsAnyTargetActive() && num < 13) {
                num ++;
                Debug.Log(num);
                SpawnCustomerAtSpawnPoint();
            } else {
                Debug.Log("모든 타겟이 비활성화되어 NPC를 더 이상 스폰하지 않습니다.");
=======
    IEnumerator SpawnCustomerRoutine()
    {
        while (true)
        {
            yield return new WaitForSeconds(7); // 4초 간격으로 NPC 스폰
            if (IsAnyTargetActive() && num < 25)
            {
                num++;
                // // Debug.Log(num);
                SpawnRandomCustomerAtSpawnPoint(); // 랜덤 NPC 생성
            }
            else
            {
                // // Debug.Log("모든 타겟이 비활성화되어 NPC를 더 이상 스폰하지 않습니다.");
>>>>>>> BEDev
                yield break; // 모든 타겟이 비활성화되면 코루틴 종료
            }
        }
        yield break;
    }
    bool IsAnyTargetActive() {
        return targetsActive.Contains(true);
    }
    void SpawnCustomerAtSpawnPoint() {
        SuperObject[] superObjects = FindObjectsOfType<SuperObject>();

        foreach (var obj in superObjects) {
            if (obj.name == "Object_44") {
                var customProperties = obj.GetComponent<SuperCustomProperties>();

<<<<<<< HEAD
                if (customProperties != null ) {
                    Debug.Log("Spawning NPC at: " + obj.name);
                    GameObject npc = Instantiate(npcPrefab, obj.transform.position, Quaternion.identity);
                    var customerScript = npc.GetComponent<CustomerManager>();
                    if (customerScript != null) {
                    customerScript.InitializeAgent();  // NavMeshAgent 초기화
                }
                break;
=======
                if (customProperties != null)
                {
                    // Debug.Log("Spawning NPC at: " + obj.name);

                    // 두 종류의 NPC 프리팹 중 하나를 랜덤으로 선택하여 생성
                    GameObject npcPrefabToSpawn = Random.Range(0, 2) == 0 ? npcPrefabMale : npcPrefabFemale;

                    GameObject npc = Instantiate(npcPrefabToSpawn, obj.transform.position, Quaternion.identity);
                    CustomerManager customerScript = npc.GetComponent<CustomerManager>();
                    if (customerScript != null)
                    {
                        customerScript.spawnPoint = obj.transform.position; // 스폰 위치 저장
                        customerScript.InitializeAgent();  // NavMeshAgent 초기화
                    }
                    break;
>>>>>>> BEDev
                }
            }
        }
    }
    public void InitializeAgent() {
        agent = GetComponent<NavMeshAgent>();
        if (agent != null) {
            agent.updateRotation = false;
            agent.updateUpAxis = false;
            MoveToSitArea();
        } else {
            // Debug.LogError("NavMeshAgent component is not found on the instantiated object.");
        }
    
    }
//     public void MoveToSitArea()
//     {

<<<<<<< HEAD
//     // agent.SetDestination(target.position);
//     if (agent == null)
//     {
//         Debug.LogError("NavMeshAgent component is not found.");
//         return;
//     }
=======
    void OnDisable() {
        FoodArriveEvent.OnFoodDelivered -= HandleFoodDelivery;
    }

private void HandleFoodDelivery(bool isCorrect, GameObject customer) {
    if (isCorrect && customer == this.gameObject) {
        SpriteRenderer customerSpriteRenderer = customer.transform.Find("Square/status").GetComponent<SpriteRenderer>();
        TextMesh customerTextMesh = customer.transform.Find("textstatus")?.GetComponent<TextMesh>();

        // 만족하는 텍스트 메시지들의 배열
        string[] satisfactionTexts = new string[] {
            "맛있네! 또 와야겠어!",
            "이렇게 맛있는 집이 있었다고?",
            "간만에 맛있게 먹었다!",
            "다음에 또 와야지..",
            "거 음식 잘하는 양반이네"
        };

        if (customerSpriteRenderer != null) {
            customerSpriteRenderer.enabled = false;

            if (customerTextMesh != null) {
                // 텍스트 메시지를 랜덤으로 선택하여 설정

                StartCoroutine(WaitAndMove(2)); // 2초간 기다리는 것으로 설정
                int randomIndex = Random.Range(0, satisfactionTexts.Length);
                customerTextMesh.text = satisfactionTexts[randomIndex];
                customerTextMesh.gameObject.SetActive(true); // TextMesh를 활성화
                ActivateTargetAgain(customer);
                }
                else {
                // Debug.LogError("TextMesh component not found on 'textstatus'.");
            }
        } else {
            // Debug.LogError("No Sprite Renderer found on customer's child object.");
        }
    } else {
        // Debug.Log("Incorrect food delivered.");
    }
}

    private void ActivateTargetAgain(GameObject customer)
    {
        // 타겟을 찾아 활성화하는 로직을 여기에 구현합니다.
        int targetIndex = targets.IndexOf(customer.transform); // 예시로 사용한 방법입니다. 실제 구현에는 타겟 인덱스를 추적하는 더 나은 방법을 사용해야 할 수 있습니다.
        if (targetIndex != -1)
        {
            targetsActive[targetIndex] = true;
            targets[targetIndex].gameObject.SetActive(true);
        }
    }

    private IEnumerator WaitAndMove(float waitTime) {
    yield return new WaitForSeconds(waitTime);
    MoveToSpawnPoint();
}

public void MoveToSpawnPoint() {
    StartCoroutine(MoveToSpawnAndDestroy()); // 코루틴 시작
}

private IEnumerator MoveToSpawnAndDestroy() {
    if (agent != null) {
        agent.SetDestination(spawnPoint);
        // Debug.Log("Returning to spawn point.");

        // NavMeshAgent가 목적지에 도착할 때까지 기다립니다.
        while (!agent.pathPending && agent.remainingDistance > 0.1f) {
            yield return null; // 다음 프레임까지 기다립니다.
        }

        // 도착 후 2초간 기다립니다.
        yield return new WaitForSeconds(2);

        // NPC 인스턴스(복제본) 삭제
        Destroy(gameObject);
    } else {
        // Debug.LogError("NavMeshAgent component is not found.");
    }
}
>>>>>>> BEDev

//     SuperObject[] sitAreas = FindObjectsOfType<SuperObject>();
//     foreach (var area in sitAreas)
//     {
//         var properties = area.GetComponent<SuperCustomProperties>();
//         if (properties != null)
//         {
//             Debug.Log("Moving NPC to area: " + area.transform.position);
//             agent.SetDestination(area.transform.position);
//             break;
//         }
//     }
// }
    public void MoveToSitArea() {

    targetsActive = new List<bool>();  // 이 줄을 추가하여 리스트를 초기화합니다.
        foreach (var target in targets) {
            targetsActive.Add(target.gameObject.activeSelf);
        }
        
    if (agent == null) {
        // Debug.LogError("NavMeshAgent component is not found.");
        return;
    }

    if (targets == null) {
        // Debug.LogError("Targets list is null.");
        return;
    }

    if (targetsActive == null) {
        // Debug.LogError("TargetsActive list is null");
    }

    List<int> activeTargets = new List<int>();
    for (int i = 0; i < targetsActive.Count; i++) {
        if (targets[i] == null) {
            // Debug.LogError($"Target at index {i} is null.");
        } else if (targetsActive[i]) {
            activeTargets.Add(i);
        }
    }

    if (activeTargets.Count > 0) {
        int randomIndex = Random.Range(0, activeTargets.Count);
        int targetIndex = activeTargets[randomIndex];
        if (targets[targetIndex] == null) {
            // Debug.LogError($"Target at index {targetIndex} is null.");
            return;
        }
        // // Debug.Log("Moving NPC to target: " + targets[targetIndex].position);
        agent.SetDestination(targets[targetIndex].position);
        // // Debug.Log("목적 설정");
        StartCoroutine(CheckIfArrived(targetIndex, targets[targetIndex].position));
        // // Debug.Log("체크 설정");

    } else {
        // Debug.Log("No active targets available.");
    }
}

private int FindNextActiveTargetIndex() {
    for (int i = 0; i < targetsActive.Count; i++) {
        if (targetsActive[i]) return i;
    }
    return -1; // 모든 타겟이 비활성화된 경우
}

    // 타겟에 도착했는지 확인하는 코루틴
    IEnumerator CheckIfArrived(int targetIndex, Vector3 targetPosition) {
        while (true) {
            yield return new WaitForSeconds(0.5f); // 주기적으로 체크

<<<<<<< HEAD
            // 타겟에 충분히 가까워졌는지 확인
                    // 타겟에 도착했다고 간주, OnTargetReached 호출
=======
    while (true) {
        // 타겟과의 현재 거리 계산
        float distance = Vector3.Distance(agent.transform.position, targetPosition);

        // 거리가 임계값 이하인지 확인
        if (distance <= thresholdDistance) {
            // // Debug.Log("Target reached: " + targetIndex);
>>>>>>> BEDev
            OnTargetReached(targetIndex);
            Debug.Log("OnTargetReached호출");
            break;
        }
    }
void OnTargetReached(int targetIndex) {
    targetsActive[targetIndex] = false; // 해당 타겟을 비활성화 상태로 변경
    targets[targetIndex].gameObject.SetActive(false); // 타겟 게임 오브젝트를 비활성화
<<<<<<< HEAD
     if (!IsAnyTargetActive()) {
        Debug.Log("모든 타겟을 방문했습니다.");
        // 여기서 필요하다면 NPC 생성을 멈출 수 있습니다.
=======
    ShowFoodImage(targetIndex);
    if (!IsAnyTargetActive()) {
        // Debug.Log("모든 타겟을 방문했습니다.");
>>>>>>> BEDev
    } else {
    }
    StartCoroutine(WaitAndChangeImageOrLeave(targetIndex, this.gameObject));
}

private IEnumerator WaitAndChangeImageOrLeave(int targetIndex, GameObject customer) {
    yield return new WaitForSeconds(20); // 15초 기다립니다.
    SpriteRenderer customerSpriteRenderer = customer.transform.Find("Square/status").GetComponent<SpriteRenderer>();
    TextMesh customerTextMesh = customer.transform.Find("textstatus")?.GetComponent<TextMesh>();
    
    string[] texts = new string[] {
            "뭐야..배고파 죽겠는데",
            "여기 별로네"
        };


    // 15초 후 실행되는 로직
    if (targetsActive[targetIndex] == false) { // 플레이어가 서빙하지 않았다면
        SpriteRenderer spriteRenderer = GetComponent<SpriteRenderer>();
        if (spriteRenderer != null) {
            customerSpriteRenderer.enabled = false;
            int randomIndex = Random.Range(0, texts.Length);
            customerTextMesh.text = texts[randomIndex];
            customerTextMesh.gameObject.SetActive(true); // TextMesh를 활성화
        }
        MoveToSpawnPoint(); // NPC를 스폰 포인트로 이동
    }
}
void ShowFoodImage(int targetIndex) {
        MenuManager menuManager = FindObjectOfType<MenuManager>(); // 메뉴 매니저 인스턴스를 찾습니다.

<<<<<<< HEAD
}
=======
        if (menuManager != null) {
            MenuItem randomFood = menuManager.GetRandomFoodItem(); // 랜덤 음식 아이템을 가져옵니다.
            if (randomFood != null && randomFood.image != null && foodImageUI != null) {
                SpriteRenderer spriteRenderer = foodImageUI.GetComponent<SpriteRenderer>();
                if (spriteRenderer != null) {
                    spriteRenderer.sprite = randomFood.image;
                    // Debug.Log("FoodImage displayed on the GameObject");
                } else {
                    // Debug.LogError("SpriteRenderer component is not found on the foodImageUI GameObject.");
                }
            }
            else {
                MoveToSpawnPoint();
            }
        }
    }
>>>>>>> BEDev
    void Update()
    {
        // NavMeshAgent의 이동 상태에 따라 애니메이션을 업데이트
        if (agent != null && animator != null)
        {
            Vector2 velocity = agent.velocity;
            animator.SetBool("Walking", velocity.magnitude > 0.1f);
            animator.SetFloat("DirX", velocity.x);
            animator.SetFloat("DirY", velocity.y);
        }
    }
}