using System.Collections;
using System.Collections.Generic;
using UnityEngine;

[CreateAssetMenu(fileName = "Item", menuName = "ScriptableObjects/Item", order = 1)]
public class SOItem : ScriptableObject
{
    public GameObject prefab; // 실제 필드에 드랍되는 prefab

    [SerializeField]
    private Sprite icon; // 아이템에 해당하는 아이콘

    public string itemName; // 아이템 이름
}
