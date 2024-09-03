/* Licensed to the Apache Software Foundation (ASF) under one
or more contributor license agreements.  See the NOTICE file
distributed with this work for additional information
regarding copyright ownership.  The ASF licenses this file
to you under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance
with the License.  You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing,
software distributed under the License is distributed on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
KIND, either express or implied.  See the License for the
specific language governing permissions and limitations
under the License. */

import { Leaf, Save } from '@vicons/ionicons5'
import type { Router } from 'vue-router'
import styles from './index.module.scss'
import DagCanvas from './dag-canvas'
import { useCDCStore } from '@/store/cdc'
import {type CdcJobDefinition, createCdcJob, updateCdcJob} from '@/api/models/cdc'
import Modal from "@/components/modal";

export default defineComponent({
  name: 'DagPage',
  setup() {
    const { t } = useLocaleHooks()
    const CDCStore = useCDCStore()
    const router: Router = useRouter()
    const message = useMessage()

    const showModalRef = ref(false)
    const CDCModalRef = ref()
    const row =  reactive({
      name: '',
      description: '',
      cdcType: 0,
      dataDelay: 60 * 1000
    })
    const name = ref('')
    const dagRef = ref() as any

    onMounted(() => {
      name.value = CDCStore.getModel.name
      if (dagRef.value && dagRef.value.graph) {
        dagRef.value.graph.fromJSON({
          cells: CDCStore.getModel.cells,
        })
      }
    })

    onUpdated(() => {
      name.value = CDCStore.getModel.name
      if (dagRef.value && dagRef.value.graph) {
        dagRef.value.graph.fromJSON({
          cells: CDCStore.getModel.cells,
        })
      }
    })

    const handleShowJobInfo = () => {
      row.name = CDCStore.getModel.name
      row.description = CDCStore.getModel.description
      row.cdcType = CDCStore.getModel.cdcType
      row.dataDelay = CDCStore.getModel.dataDelay
      showModalRef.value = true
    }

    const handleConfirm = async (model: any) => {
      await CDCModalRef.value.formRef.validate()
      const rawModel = useCDCStore().getModel
      rawModel.name = model.name
      rawModel.description = model.description
      rawModel.cdcType = model.cdcType
      rawModel.dataDelay = model.dataDelay
      CDCStore.setModel(rawModel)
      showModalRef.value = false
    }

    const handleSave = () => {
      const editMode = CDCStore.getModel.editMode
      const config = dagRef.value.graph.toJSON()
      if(config.cells.length == 0) {
        message.warning(t('cdc.cdc_job_save_not_null'))
        return
      }
      const jobParam: CdcJobDefinition = {
        name: CDCStore.getModel.name,
        description: CDCStore.getModel.description,
        cdcType: CDCStore.getModel.cdcType,
        config: JSON.stringify(config),
        dataDelay: CDCStore.getModel.dataDelay
      }
      if (editMode === 'edit') {
        jobParam.id = CDCStore.getModel.id
        updateCdcJob(jobParam)
          .then(() => {
            router.push({ path: '/cdc_ingestion' })
          })
      }
      else {
        createCdcJob(jobParam)
          .then(() => {
            router.push({ path: '/cdc_ingestion' })
          })
      }
    }

    const handleJump = () => {
      router.push({ path: '/cdc_ingestion' })
    }
    return {
      t,
      name,
      row,
      handleShowJobInfo,
      handleConfirm,
      showModalRef,
      CDCModalRef,
      handleSave,
      dagRef,
      handleJump,
    }
  },
  render() {
    return (
      <n-card>
        <n-space vertical size={24}>
          <n-card>
            <div class={styles['title-bar']}>
              <n-space align="center">
                <n-icon component={Leaf} color="#2F7BEA" size="18" />
                <span class={styles.title} onClick={this.handleJump}>
                  {this.t('cdc.cdc_job_definition')}
                  {this.name ? ` - ${this.name}` : ''}
                </span>
              </n-space>
              <div class={styles.operation}>
                <n-space>
                  <n-button onClick={this.handleShowJobInfo}>
                    {this.t('cdc.cdc_job_info')}
                  </n-button>
                  <n-button type="primary" onClick={this.handleSave}>
                    {this.t('cdc.save_synchronization_job')}
                  </n-button>
                </n-space>
              </div>
            </div>
            {this.showModalRef && (
                <Modal
                    ref="CDCModalRef"
                    row={this.row}
                    showModal={this.showModalRef}
                    title={this.t('cdc.cdc_job_info')}
                    formType="CDCLIST"
                    onCancel={() => (this.showModalRef = false)}
                    onConfirm={this.handleConfirm}
                />
            )}
          </n-card>
          <DagCanvas ref="dagRef"></DagCanvas>
        </n-space>
      </n-card>
    )
  },
})
